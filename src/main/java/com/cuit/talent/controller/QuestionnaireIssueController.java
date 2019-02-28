package com.cuit.talent.controller;

import com.cuit.talent.service.QuestionnaireIssueService;
import com.cuit.talent.utils.valueobj.Message;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionnaireIssueController {

    @Autowired
    private QuestionnaireIssueService questionnaireIssueService;

    @RequestMapping(value = "/api/questionnaireIssue/publishQuestionnaire", method = RequestMethod.POST)
    public ResponseEntity publishQuestionnaire(@RequestBody JsonNode jsonNode ){
        Integer questionnaireId = Integer.parseInt(jsonNode.path("questionnaireId").textValue());
        Integer userId = Integer.parseInt(jsonNode.path("userId").textValue());
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


    @RequestMapping(value = "/api/questionnaire/findAllQuestionnaireIssueByUserId", method = RequestMethod.GET)
    public ResponseEntity findAllQuestionnaireIssueByUserId(Integer userId){
        Message message =  new Message();
        try {
             message.setData(questionnaireIssueService.findAllQuestionnaireIssueByUserId(userId));
             message.setMsg("获取自己发布的所有问卷成功");
             message.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("获取自己发布的所有问卷失败");
        }

        return ResponseEntity.ok(message);
    }


    @RequestMapping(value = "/api/questionnaire/findAllQuestionnaireIssueAndFillNumberByUserId", method = RequestMethod.GET)
    public ResponseEntity findAllQuestionnaireIssueAndFillNumberByUserId(Integer userId){
        Message message =  new Message();
        try {
            message.setData(questionnaireIssueService.findAllQuestionnaireIssueAndFillNumberByUserId(userId));
            message.setMsg("获取自己发布的所有问卷及所填次数成功");
            message.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("获取失败");
        }

        return ResponseEntity.ok(message);
    }
    @RequestMapping(value = "/api/questionnaire/deleteQuestionnaireIssueById", method = RequestMethod.POST)
    public ResponseEntity deleteQuestionnaireIssueByUserId(@RequestBody JsonNode jsonNode){
        Message message =  new Message();
        try {
            Integer id = Integer.parseInt(jsonNode.path("id").textValue());
            questionnaireIssueService.deleteQuestionnaireIssueById(id);
            message.setMsg("删除问卷成功");
            message.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("删除问卷失败");
        }

        return ResponseEntity.ok(message);
    }


    @RequestMapping(value = "/api/questionnaire/findQuestionnaireCanFill", method = RequestMethod.GET)
    public ResponseEntity findQuestionnaireCanFill(Integer userId){
        Message message =  new Message();
        try {
            message.setData(questionnaireIssueService.findQuestionnaireCanFill(userId));
            message.setMsg("获取可填问卷成功");
            message.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("获取可填问卷失败");
        }

        return ResponseEntity.ok(message);
    }

}
