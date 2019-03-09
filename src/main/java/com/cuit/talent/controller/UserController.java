package com.cuit.talent.controller;

import com.alibaba.fastjson.JSONObject;
import com.cuit.talent.model.User;
import com.cuit.talent.utils.valueobj.Message;
import com.cuit.talent.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public ResponseEntity userLogin(@RequestBody JsonNode jsonNode){
        String studentId = jsonNode.path("studentId").textValue();
        String password = jsonNode.path("password").textValue();
        Message message = userService.ensureUser(studentId, password);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/user/create", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody JSONObject jsonObject){
        ArrayList<Map<String, Object>> userInformationList = (ArrayList) jsonObject.get("userInformation");
        Message message = userService.createUser(userInformationList);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/user/find", method = RequestMethod.GET)
    public ResponseEntity findUserByStudentId(@RequestParam(value = "userId")Integer userId){
        User user = userService.findUser(userId);
        Message message = new Message();
        if (user == null){
            message.setCode(0);
            message.setMsg("不存在该用户");
        }
        message.setMsg("查询成功");
        message.setCode(1);
        user.setPassword(null);
        message.setData(user);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/user/rePassword",method = RequestMethod.POST)
    public ResponseEntity updateUserPassword(@RequestBody JsonNode jsonNode){
        Message message = userService.updateUserPassword(jsonNode);
        return ResponseEntity.ok(message);
    }
    @RequestMapping(value = "/api/user/teacherFindClass",method = RequestMethod.GET)
    public ResponseEntity teacherFindClass(@RequestParam(value = "teacherId")String teacherId){
        Message message = userService.teacherFindClass(teacherId);
        return ResponseEntity.ok(message);
    }
    @RequestMapping(value = "/api/teacher/create", method = RequestMethod.POST)
    public ResponseEntity createTeacher(@RequestBody JSONObject jsonObject){
        ArrayList<Map<String, Object>> userInformationList = (ArrayList) jsonObject.get("teacherInformation");
        Message message = userService.createTeacher(userInformationList);
        return ResponseEntity.ok(message);
    }
}
