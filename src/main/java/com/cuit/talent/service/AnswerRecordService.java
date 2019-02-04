package com.cuit.talent.service;

import com.cuit.talent.model.AnswerRecord;
import com.cuit.talent.model.QAnswerRecord;
import com.cuit.talent.model.QuestionnaireIssue;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.AnswerRecordDetailsRepository;
import com.cuit.talent.repository.AnswerRecordRepository;
import com.cuit.talent.repository.QuestionnaireIssueRepository;
import com.cuit.talent.utils.valueobj.Answer;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerRecordService {

    @Autowired
    private AnswerRecordRepository answerRecordRepository;
    @Autowired
    private AnswerRecordDetailsRepository answerRecordDetailsRepository;

    @Autowired
    private QuestionnaireIssueRepository questionnaireIssueRepository;

    public Integer saveAnswerRecord(Integer userId, Integer questionnaireIssueId){

        AnswerRecord answerRecord = new AnswerRecord();
        User user = new User();
        user.setId(userId);
        answerRecord.setUserByUserId(user);
        QuestionnaireIssue questionnaireIssue = new QuestionnaireIssue();
        questionnaireIssue.setId(questionnaireIssueId);
        answerRecord.setQuestionnaireIssueByQuestionnaireIssueId(questionnaireIssue);
        answerRecord.setIsFill(0);
        answerRecordRepository.save(answerRecord);
       return  answerRecord.getId();
    }

    public boolean checkAnswerRecordExist(Integer userId,Integer questionnaireIssueId){
        QAnswerRecord qAnswerRecord = QAnswerRecord.answerRecord;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecord.userByUserId.id.eq(userId));
        booleanBuilder.and(qAnswerRecord.questionnaireIssueByQuestionnaireIssueId.id.eq(questionnaireIssueId));
        Iterable<AnswerRecord> answerRecords = answerRecordRepository.findAll(booleanBuilder);
        while ( answerRecords.iterator().hasNext()){
           if(answerRecords.iterator().next().getIsFill()==1){
               return false;
           }
        }
        return  true;


    }

    public boolean checkCanFillQuestionnaireIssue(QuestionnaireIssue questionnaireIssue,Integer userId){
        QAnswerRecord qAnswerRecord = QAnswerRecord.answerRecord;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecord.questionnaireIssueByQuestionnaireIssueId.eq(questionnaireIssue));
        booleanBuilder.and(qAnswerRecord.userByUserId.id.eq(userId));
        Iterable<AnswerRecord> answerRecords = answerRecordRepository.findAll(booleanBuilder);
        if(answerRecords.iterator().hasNext()){
            return false;
        }else {
            return true;
        }
    }

}
