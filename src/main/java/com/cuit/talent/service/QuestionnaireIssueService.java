package com.cuit.talent.service;

import com.cuit.talent.model.Questionnaire;
import com.cuit.talent.model.QuestionnaireIssue;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.QuestionnaireIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


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
}
