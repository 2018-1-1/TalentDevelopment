package com.cuit.talent.controller;

import com.cuit.talent.service.QuestionnaireIssueService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionnaireIssueController {

    @Autowired
    private QuestionnaireIssueService questionnaireIssueService;

    @RequestMapping(value = "/questionnaireIssue/publishQuestionnaire", method = RequestMethod.POST)
    public ResponseEntity publishQuestionnaire(Integer userId,Integer questionnaireId ){
        Message message =  new Message();
       try {
            questionnaireIssueService.addQuestionnaireIssue(userId,questionnaireId);
            message.setMsg("发布成功");
            message.setCode(1);
       }catch (Exception e){
           e.printStackTrace();
           message.setCode(0);
           message.setMsg("返回失败，请稍后再试");
       }

        return ResponseEntity.ok(message);
    }


//    @RequestMapping(value = "/questionnaire/findAllQuestionsByQuestionnaireId", method = RequestMethod.GET)
//    public ResponseEntity findAllQuestionsByQuestionnaireId(Integer questionnaireId){
//        Message message =  new Message();
//        try {
//
//            message.setCode(1);
//        }catch (Exception e){
//            e.printStackTrace();
//            message.setCode(0);
//            message.setMsg("返回失败，请稍后再试");
//        }
//
//        return ResponseEntity.ok(message);
//    }
}
