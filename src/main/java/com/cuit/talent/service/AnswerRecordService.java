package com.cuit.talent.service;

import com.cuit.talent.model.AnswerRecord;
import com.cuit.talent.model.QAnswerRecord;
import com.cuit.talent.model.QuestionnaireIssue;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.AnswerRecordDetailsRepository;
import com.cuit.talent.repository.AnswerRecordRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerRecordService {

    @Autowired
    private AnswerRecordRepository answerRecordRepository;
    @Autowired
    private AnswerRecordDetailsRepository answerRecordDetailsRepository;

    public void saveAnswerRecord(Integer userId,Integer questionnaireIssueId){

        AnswerRecord answerRecord = new AnswerRecord();
        User user = new User();
        user.setId(userId);
        answerRecord.setUserByUserId(user);
        QuestionnaireIssue questionnaireIssue = new QuestionnaireIssue();
        questionnaireIssue.setId(questionnaireIssueId);
        answerRecord.setQuestionnaireIssueByQuestionnaireIssueId(questionnaireIssue);
        answerRecord.setIsFill(1);
        answerRecordRepository.save(answerRecord);
        System.out.println("-----------------"+answerRecord.getId());
    }

    public boolean checkAnswerRecordExist(Integer userId,Integer questionnaireIssueId){
        QAnswerRecord qAnswerRecord = QAnswerRecord.answerRecord;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecord.userByUserId.id.eq(userId));
        booleanBuilder.and(qAnswerRecord.questionnaireIssueByQuestionnaireIssueId.id.eq(questionnaireIssueId));
        Iterable<AnswerRecord> answerRecords = answerRecordRepository.findAll(booleanBuilder);
        if( answerRecords.iterator().hasNext()){
              return false;
        }
        return  true;


    }
}
