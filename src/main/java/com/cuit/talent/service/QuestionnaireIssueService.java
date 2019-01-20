package com.cuit.talent.service;

import com.cuit.talent.model.QQuestionnaireIssue;
import com.cuit.talent.model.Questionnaire;
import com.cuit.talent.model.QuestionnaireIssue;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.QuestionnaireIssueRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import javafx.util.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionnaireIssueService {

    @Autowired
    private QuestionnaireIssueRepository questionnaireIssueRepository;

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
            return questionnaireIssues;
        }else{
            return null;
        }
    }

    public List<QuestionnaireIssue> findAllQuestionnaireIssue(){
        return questionnaireIssueRepository.findAll();
    }

//    private List<QuestionnaireIssue>   findQuestionnaireIssuesCanFilled(){
//        List<QuestionnaireIssue> allQuestionnaireIssues = findAllQuestionnaireIssue();
//        for(QuestionnaireIssue questionnaireIssue :allQuestionnaireIssues){
//
//        }
//    }
}
