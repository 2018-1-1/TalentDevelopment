package com.cuit.talent.controller;

import com.cuit.talent.repository.QuestionBankRepository;
import com.cuit.talent.service.AnswerRecordDetailsService;
import com.cuit.talent.service.AnswerRecordService;
import com.cuit.talent.utils.valueobj.Answer;
import com.cuit.talent.utils.valueobj.Message;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class AnswerRecordController {
    @Autowired
    private AnswerRecordService answerRecordService;

    @Autowired
    private AnswerRecordDetailsService answerRecordDetailsService;
    @Autowired
    private QuestionBankRepository questionBankRepository;

    @RequestMapping(value = "/api/answerRecord/saveAnswerRecord", method = RequestMethod.POST)
    public ResponseEntity saveAnswerRecord(@RequestBody JsonNode jsonNode) {

        Message message = new Message();
        try {
            Integer userId = jsonNode.path("userId").asInt();
            Integer questionnaireIssueId = jsonNode.path("questionnaireIssueId").asInt();
            String questionnaireName = jsonNode.path("questionnaireName").textValue();
            Iterator<JsonNode> iterable = jsonNode.path("answerList").iterator();
            List<Answer> answerList = new ArrayList<>();
            Answer answer;
            while (iterable.hasNext()) {
                answer = new Answer();
                JsonNode jsonNode1 = iterable.next();
                Integer n = jsonNode1.path("id").asInt();
                answer.setQuestionBankId(n);
                Integer questionType = questionBankRepository.getOne(n).getQuestionTypeByQuestionTypeId().getId();

                switch (questionType) {
                    case 1:
                        answer.setAnswerValue(jsonNode1.get("answer").asInt());
                        break;
                    case 2:
                        List<Integer> list = new ArrayList<>();
                        if (jsonNode1.get("answer").isArray()) {
                            for (JsonNode objNode : jsonNode1.get("answer")) {
                                list.add(objNode.asInt());
                            }
                            answer.setAnswerValue(list);
                        }
                        break;
                    case 3:
                        answer.setAnswerValue(jsonNode1.get("answer").textValue());
                        break;
                    default:
                        break;
                }
                answerList.add(answer);
            }
            if (answerRecordService.checkAnswerRecordExist(userId, questionnaireIssueId)) {
                Integer answerRecordId = answerRecordService.saveAnswerRecord(userId, questionnaireIssueId);
                answerRecordDetailsService.saveAnswerRecordDetails(answerList, answerRecordId);
                message.setCode(1);
                message.setMsg("保存成功");
            } else {
                message.setCode(2);
                message.setMsg("无法保存记录，该记录已经存在");
            }

        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("保存失败，请稍后再试");
        }
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/answerRecord/findAllRecordsByQuestionnaireIssue", method = RequestMethod.GET)
    public ResponseEntity findAllQuestionsByQuestionnaireId(Integer questionnaireIssueId) {
        Message message = new Message();
        try {
            message.setMsg("查找问卷的所有答题详情成功");
            message.setData(answerRecordService.findAllRecordsByQuestionnaireIssue(questionnaireIssueId));
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMsg("返回失败，请稍后再试");
        }
        return ResponseEntity.ok(message);
    }
}
