package com.cuit.talent.service;

import com.cuit.talent.model.*;
import com.cuit.talent.repository.QuestionnaireIssueRepository;
import com.cuit.talent.utils.valueobj.QuestionnaireIssueAndFillNumber;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionnaireIssueService {

    @Autowired
    private QuestionnaireIssueRepository questionnaireIssueRepository;

    @Autowired
    private UserGradeService userGradeService;
    @Autowired
    private AnswerRecordService answerRecordService;

    public void addQuestionnaireIssue(Integer userId,Integer questionnaireId ){

        QuestionnaireIssue questionnaireIssue = new QuestionnaireIssue();
        User user = new User();
        user.setId(userId);
        questionnaireIssue.setUserByUserId(user);
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(questionnaireId);
        questionnaireIssue.setQuestionnaireByQuestionnaireId(questionnaire);
        java.util.Date date1 = new  java.util.Date();
        Timestamp date = new Timestamp(date1.getTime());
        questionnaireIssue.setIssueTime(date);
        questionnaireIssueRepository.save(questionnaireIssue);

    }

    public List<QuestionnaireIssue> findAllQuestionnaireIssueByUserId(Integer userId){
        if(userId != null) {
            QQuestionnaireIssue qQuestionnaireIssue = QQuestionnaireIssue.questionnaireIssue;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(qQuestionnaireIssue.userByUserId.id.eq(userId));
            Iterable<QuestionnaireIssue> questionnaireIssueIterable = questionnaireIssueRepository.findAll(booleanBuilder);
            List<QuestionnaireIssue> questionnaireIssues = Lists.newArrayList(questionnaireIssueIterable);
            for(QuestionnaireIssue questionnaireIssue:questionnaireIssues){
                questionnaireIssue.getUserByUserId().setPassword(null);
                questionnaireIssue.getUserByUserId().setRoleByRoleId(null);
            }

            return questionnaireIssues;
        }else{
            return null;
        }
    }

    public List<QuestionnaireIssueAndFillNumber> findAllQuestionnaireIssueAndFillNumberByUserId(Integer userId){
        if(userId != null) {
            Sort sort = new Sort(Sort.Direction.DESC, "issueTime");
            QQuestionnaireIssue qQuestionnaireIssue = QQuestionnaireIssue.questionnaireIssue;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(qQuestionnaireIssue.userByUserId.id.eq(userId));

            Iterable<QuestionnaireIssue> questionnaireIssueIterable = questionnaireIssueRepository.findAll(booleanBuilder,sort);
            List<QuestionnaireIssue> questionnaireIssues = Lists.newArrayList(questionnaireIssueIterable);
            List<QuestionnaireIssueAndFillNumber> questionnaireIssueAndFillNumberList = new ArrayList<>();
            QuestionnaireIssueAndFillNumber questionnaireIssueAndFillNumber;
            for(QuestionnaireIssue questionnaireIssue:questionnaireIssues){
                questionnaireIssue.getUserByUserId().setPassword(null);
                questionnaireIssue.getUserByUserId().setRoleByRoleId(null);

                questionnaireIssueAndFillNumber = new QuestionnaireIssueAndFillNumber();
                questionnaireIssueAndFillNumber.setFillNumber(answerRecordService.findQuestionnaireIssueFillNumberByQuestionnaireIssueId(questionnaireIssue.getId()));
                questionnaireIssueAndFillNumber.setQuestionnaireIssue(questionnaireIssue);
                questionnaireIssueAndFillNumberList.add(questionnaireIssueAndFillNumber);

            }

            return questionnaireIssueAndFillNumberList;
        }else{
            return null;
        }
    }

    public List<QuestionnaireIssue> findAllQuestionnaireIssue(){
        return questionnaireIssueRepository.findAll();
    }


    public List<QuestionnaireIssue> findQuestionnaireCanFill(Integer userId){
        List<QuestionnaireIssue> canFillQuestionnaireIssues = new ArrayList<>();
        List<UserGrade> myUserGrades = userGradeService.findByUserId(userId);
        for(QuestionnaireIssue questionnaireIssue:findAllQuestionnaireIssue()){
            boolean isSameGrade = false;
            List<UserGrade> issueGrades = userGradeService.findByUserId(questionnaireIssue.getUserByUserId().getId());
            for(UserGrade myUserGrade:myUserGrades){
                for(UserGrade issueGrade:issueGrades){
                    if(issueGrade.getGradeByGradeId().equals(myUserGrade.getGradeByGradeId())){
                        isSameGrade = true;
                        break;
                    }
                }
                if(isSameGrade){
                    break;
                }
            }

            if(isSameGrade&&answerRecordService.checkCanFillQuestionnaireIssue(questionnaireIssue,userId)){
                questionnaireIssue.getUserByUserId().setPassword(null);
                questionnaireIssue.getUserByUserId().setRoleByRoleId(null);
                canFillQuestionnaireIssues.add(questionnaireIssue);
            }
        }

        return  canFillQuestionnaireIssues;
    }


    public void deleteQuestionnaireIssueById(Integer id){
        questionnaireIssueRepository.deleteById(id);
    }

//    private List<QuestionnaireIssue>   findQuestionnaireIssuesCanFilled(){
//        List<QuestionnaireIssue> allQuestionnaireIssues = findAllQuestionnaireIssue();
//        for(QuestionnaireIssue questionnaireIssue :allQuestionnaireIssues){
//
//        }
//    }
}
