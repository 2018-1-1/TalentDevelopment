package com.cuit.talent.service;

import com.cuit.talent.model.*;
import com.cuit.talent.repository.*;
import com.cuit.talent.utils.valueobj.RecordCount;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class AnswerRecordService {

    @Autowired
    private AnswerRecordRepository answerRecordRepository;
    @Autowired
    private AnswerRecordDetailsRepository answerRecordDetailsRepository;

    @Autowired
    private QuestionnaireIssueRepository questionnaireIssueRepository;

    @Autowired
    private SelectQuestionRepository selectQuestionRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;


    public List<Map<String, Object>> findAllRecordsByQuestionnaireIssue(Integer questionnaireIssueId) {
        Optional<QuestionnaireIssue> questionnaireIssue1 = questionnaireIssueRepository.findById(questionnaireIssueId);
        Integer questionnaireId = questionnaireIssue1.get().getQuestionnaireByQuestionnaireId().getId();

        QSelectQuestion qSelectQuestion = QSelectQuestion.selectQuestion;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSelectQuestion.questionnaireByQuestionnaireId.id.eq(questionnaireId));
        Iterable<SelectQuestion> selectQuestionIterable = selectQuestionRepository.findAll(booleanBuilder);
        List<SelectQuestion> selectQuestionList = Lists.newArrayList(selectQuestionIterable);

        List<Map<String, Object>> body = new ArrayList<>();
        Map<String, Object> map = null;

        for (SelectQuestion selectQuestion : selectQuestionList) {

            if (selectQuestion.getQuestionBankByQuestionBankId().getQuestionTypeByQuestionTypeId().getId() == 3) {
                continue;
            }
            QQuestionAnswer qQuestionAnswer = QQuestionAnswer.questionAnswer;
            BooleanBuilder booleanBuilder1 = new BooleanBuilder();
            booleanBuilder1.and(qQuestionAnswer.questionBankByQuestionBankId.eq(selectQuestion.getQuestionBankByQuestionBankId()));
            Iterable<QuestionAnswer> questionAnswers = questionAnswerRepository.findAll(booleanBuilder1);

            List<QuestionAnswer> questionAnswerList = Lists.newArrayList(questionAnswers);
            RecordCount recordCount;
            List<RecordCount> recordCounts = new ArrayList<>();
            for (QuestionAnswer questionAnswer : questionAnswerList) {
                recordCount = new RecordCount();
                recordCount.setQuestionAnswer(questionAnswer);
                recordCount.setCount(countAnswerRecord(questionnaireIssueId, questionAnswer));
                recordCounts.add(recordCount);
            }

            map = new HashMap<>();
            map.put("selectQuestion", selectQuestion);
            map.put("recordCounts", recordCounts);
            body.add(map);
        }
        return body;
    }

    public Integer saveAnswerRecord(Integer userId, Integer questionnaireIssueId) {

        AnswerRecord answerRecord = new AnswerRecord();
        User user = new User();
        user.setId(userId);
        answerRecord.setUserByUserId(user);
        QuestionnaireIssue questionnaireIssue = new QuestionnaireIssue();
        questionnaireIssue.setId(questionnaireIssueId);
        answerRecord.setQuestionnaireIssueByQuestionnaireIssueId(questionnaireIssue);
        answerRecord.setIsFill(0);
        answerRecordRepository.save(answerRecord);
        return answerRecord.getId();
    }

    public boolean checkAnswerRecordExist(Integer userId, Integer questionnaireIssueId) {
        QAnswerRecord qAnswerRecord = QAnswerRecord.answerRecord;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecord.userByUserId.id.eq(userId));
        booleanBuilder.and(qAnswerRecord.questionnaireIssueByQuestionnaireIssueId.id.eq(questionnaireIssueId));
        Iterable<AnswerRecord> answerRecords = answerRecordRepository.findAll(booleanBuilder);
        while (answerRecords.iterator().hasNext()) {
            if (answerRecords.iterator().next().getIsFill() == 1) {
                return false;
            }
        }
        return true;


    }

    public boolean checkCanFillQuestionnaireIssue(QuestionnaireIssue questionnaireIssue, Integer userId) {
        QAnswerRecord qAnswerRecord = QAnswerRecord.answerRecord;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecord.questionnaireIssueByQuestionnaireIssueId.eq(questionnaireIssue));
        booleanBuilder.and(qAnswerRecord.userByUserId.id.eq(userId));
        Iterable<AnswerRecord> answerRecords = answerRecordRepository.findAll(booleanBuilder);
        if (answerRecords.iterator().hasNext()) {
            return false;
        } else {
            return true;
        }
    }

    public int countAnswerRecord(Integer questionnaireIssueId, QuestionAnswer questionAnswer) {
        int count = 0;
        boolean isSelect = true;
        QAnswerRecordDetails qAnswerRecordDetails = QAnswerRecordDetails.answerRecordDetails;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecordDetails.answerRecordByAnswerRecordId.questionnaireIssueByQuestionnaireIssueId.id.eq(questionnaireIssueId));
        booleanBuilder.and(qAnswerRecordDetails.questionBankByQuestionBankId.id.eq(questionAnswer.getQuestionBankByQuestionBankId().getId()));
        switch (questionAnswer.getAnswerKey()) {
            case 1:
                booleanBuilder.and(qAnswerRecordDetails.optionA.eq(1));
                break;
            case 2:
                booleanBuilder.and(qAnswerRecordDetails.optionB.eq(1));
                break;
            case 3:
                booleanBuilder.and(qAnswerRecordDetails.optionC.eq(1));
                break;
            case 4:
                booleanBuilder.and(qAnswerRecordDetails.optionD.eq(1));
                break;
            case 5:
                booleanBuilder.and(qAnswerRecordDetails.optionE.eq(1));
                break;
            case 6:
                booleanBuilder.and(qAnswerRecordDetails.optionF.eq(1));
                break;
            default:
                isSelect = false;
                break;
        }
        if (isSelect) {
            count = (int) answerRecordDetailsRepository.count(booleanBuilder);
        }
        return count;
    }


    public Long findQuestionnaireIssueFillNumberByQuestionnaireIssueId(Integer questionnaireIssueId){
        QAnswerRecord qAnswerRecord = QAnswerRecord.answerRecord;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qAnswerRecord.questionnaireIssueByQuestionnaireIssueId.id.eq(questionnaireIssueId));
        booleanBuilder.and(qAnswerRecord.isFill.eq(1));
        return answerRecordRepository.count(booleanBuilder);
    }



}
