package com.cuit.talent.controller;

import com.cuit.talent.service.AnswerRecordService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerRecordController {
    @Autowired
    private AnswerRecordService answerRecordService;

    @RequestMapping(value = "/answerRecord/saveAnswerRecord", method = RequestMethod.POST)
    public ResponseEntity saveAnswerRecord(Integer userId,Integer questionnaireIssueId){
        Message message =  new Message();
        try {
            if(answerRecordService.checkAnswerRecordExist(userId,questionnaireIssueId)) {
                answerRecordService.saveAnswerRecord(userId, questionnaireIssueId);
                message.setCode(1);
                message.setMsg("保存成功");
            }else{
                message.setCode(2);
                message.setMsg("无法保存记录，该记录已经存在");
            }

        }catch (Exception e){
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("返回失败，请稍后再试");
        }

        return ResponseEntity.ok(message);
    }
}
