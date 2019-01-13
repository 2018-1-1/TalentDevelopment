package com.cuit.talent.controller;

import com.alibaba.fastjson.JSONObject;
import com.cuit.talent.utils.valueobj.Message;
import com.cuit.talent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity userLogin(@RequestParam("studentId")String studentId,@RequestParam("password")String password){
        Message message = userService.ensureUser(studentId, password);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toJSONString());
        return ResponseEntity.ok(null);
    }
}
