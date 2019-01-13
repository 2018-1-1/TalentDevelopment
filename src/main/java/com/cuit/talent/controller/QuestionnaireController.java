package com.cuit.talent.controller;

import com.cuit.talent.service.QuestionnaireService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "/questionnaire/findAllQuestionnaire", method = RequestMethod.GET)
    public ResponseEntity findAllQuestionnaire(){
        Message message =  new Message();
        try {
            message.setData(questionnaireService.selectAllQuestionnaire());
            message.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("返回失败，请稍后再试");
        }

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/questionnaire/findAllQuestionsByQuestionnaireId", method = RequestMethod.GET)
    public ResponseEntity findAllQuestionsByQuestionnaireId(Integer questionnaireId){
        Message message =  new Message();
        try {
            message.setData(questionnaireService.findSelectQuestionByQuestionnaireId(questionnaireId));
            message.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("返回失败，请稍后再试");
        }
        return ResponseEntity.ok(message);
    }
}
