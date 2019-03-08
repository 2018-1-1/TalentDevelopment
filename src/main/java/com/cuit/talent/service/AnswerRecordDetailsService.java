package com.cuit.talent.service;

import com.cuit.talent.model.AnswerRecord;
import com.cuit.talent.model.AnswerRecordDetails;
import com.cuit.talent.model.QuestionBank;
import com.cuit.talent.repository.AnswerRecordDetailsRepository;
import com.cuit.talent.repository.AnswerRecordRepository;
import com.cuit.talent.repository.QuestionBankRepository;
import com.cuit.talent.utils.valueobj.Answer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerRecordDetailsService {

    @Autowired
    private AnswerRecordDetailsRepository answerRecordDetailsRepository;
    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private AnswerRecordRepository answerRecordRepository;

    public void saveAnswerRecordDetails(List<Answer> answerList, Integer answerRecordId) {
        List<AnswerRecordDetails> answerRecordDetailsList = new ArrayList<>();
        AnswerRecordDetails answerRecordDetails;
        QuestionBank questionBank;
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setId(answerRecordId);
        for (Answer answer : answerList) {
            questionBank = new QuestionBank();
            questionBank.setId(answer.getQuestionBankId());
            answerRecordDetails = new AnswerRecordDetails();
            answerRecordDetails.setQuestionBankByQuestionBankId(questionBank);
            answerRecordDetails.setAnswerRecordByAnswerRecordId(answerRecord);
            Integer questionType = questionBankRepository.getOne(answer.getQuestionBankId()).getQuestionTypeByQuestionTypeId().getId();

            if (questionType == 1) {
                saveRadioAnswer(answer, answerRecordDetails);
            }

            if (questionType == 2) {
                saveCheckboxAnswer(answer, answerRecordDetails);
            }

            if (questionType == 3) {
                saveTextAnswer(answer, answerRecordDetails);
            }

            answerRecordDetailsList.add(answerRecordDetails);

        }
        answerRecordDetailsRepository.saveAll(answerRecordDetailsList);
        AnswerRecord answerRecord2 = answerRecordRepository.getOne(answerRecordId);
        answerRecord2.setIsFill(1);
        answerRecordRepository.saveAndFlush(answerRecord2);

    }

    private void saveRadioAnswer(Answer answer, AnswerRecordDetails answerRecordDetails) {
        setSelect(answerRecordDetails,(Integer) answer.getAnswerValue());
    }

    private void saveCheckboxAnswer(Answer answer, AnswerRecordDetails answerRecordDetails) {
        List<Integer> list = (List<Integer>) answer.getAnswerValue();
        for (Integer select : list) {
            setSelect(answerRecordDetails, select);
        }
    }

    private void saveTextAnswer(Answer answer, AnswerRecordDetails answerRecordDetails) {
        answerRecordDetails.setFillBlankAnswer(answer.getAnswerValue().toString());
    }

    private void setSelect(AnswerRecordDetails answerRecordDetails, Integer select) {
        switch (select) {
            case 1:
                answerRecordDetails.setOptionA(1);
                break;
            case 2:
                answerRecordDetails.setOptionB(1);
                break;
            case 3:
                answerRecordDetails.setOptionC(1);
                break;
            case 4:
                answerRecordDetails.setOptionD(1);
                break;
            case 5:
                answerRecordDetails.setOptionE(1);
                break;
            case 6:
                answerRecordDetails.setOptionF(1);
                break;
            case 7:
                answerRecordDetails.setOptionG(1);
                break;
            case 8:
                answerRecordDetails.setOptionH(1);
                break;
            case 9:
                answerRecordDetails.setOptionI(1);
                break;
            case 10:
                answerRecordDetails.setOptionJ(1);
                break;
            default:
                break;
        }
    }
}

