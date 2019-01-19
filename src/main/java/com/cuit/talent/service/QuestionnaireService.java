package com.cuit.talent.service;

import com.cuit.talent.model.*;
import com.cuit.talent.repository.QuestionAnswerRepository;
import com.cuit.talent.repository.QuestionnaireRepository;
import com.cuit.talent.repository.SelectQuestionRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private SelectQuestionRepository selectQuestionRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;


    public List<Questionnaire> selectAllQuestionnaire(){
        return  questionnaireRepository.findAll();
    }

    public List<Map<String,Object>> findSelectQuestionByQuestionnaireId(Integer questionnaireId){
        QSelectQuestion qSelectQuestion = QSelectQuestion.selectQuestion;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSelectQuestion.questionnaireByQuestionnaireId.id.eq(questionnaireId));
        Iterable<SelectQuestion> selectQuestionIterable = selectQuestionRepository.findAll(booleanBuilder);
        List<SelectQuestion> selectQuestionList = Lists.newArrayList(selectQuestionIterable);
        List<Map<String,Object>> body = new ArrayList<>();
        Map<String,Object> map = null;
        for(SelectQuestion selectQuestion: selectQuestionList){
            map = new HashMap<>();
            map.put("selectQuestion",selectQuestion);
            QQuestionAnswer qQuestionAnswer = QQuestionAnswer.questionAnswer;
            BooleanBuilder booleanBuilder1 = new BooleanBuilder();
            booleanBuilder1.and(qQuestionAnswer.questionBankByQuestionBankId.eq(selectQuestion.getQuestionBankByQuestionBankId()));
            Iterable<QuestionAnswer> questionAnswers = questionAnswerRepository.findAll(booleanBuilder1);
            map.put("questionAnswers", Lists.newArrayList(questionAnswers));
            body.add(map);
        }
        return body;
    }
}
