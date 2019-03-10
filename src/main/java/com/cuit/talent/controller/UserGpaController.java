package com.cuit.talent.controller;

import com.alibaba.fastjson.JSONObject;
import com.cuit.talent.model.UserGpa;
import com.cuit.talent.service.UserGpaService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class UserGpaController {
    @Autowired
    private UserGpaService userGpaService;

    @RequestMapping(value = "/api/create/gpa", method = RequestMethod.POST)
    public ResponseEntity createStudentGpa(@RequestBody JSONObject jsonObject){
        ArrayList<Map<String, Object>> studentsGpa = (ArrayList) jsonObject.get("studentsGpa");
        Message message = userGpaService.createUserGpa(studentsGpa);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/find/gpas", method = RequestMethod.GET)
    public ResponseEntity findStudentsGpas(Integer userId){
        Message message =new Message();
        if (userId.equals(null)){
            message.setCode(0);
            message.setMsg("userId不能为空");
        }else {
            List<UserGpa> userGpaList = userGpaService.findUserGpasByUserId(userId);
            userGpaList.forEach(i->i.getUserByUserId().setPassword(null));
            message.setMsg("ok");
            message.setData(userGpaList);
            message.setCode(1);
        }
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/find/gpa", method = RequestMethod.GET)
    public ResponseEntity findStudentsGpa(Integer userId, String semester){
        Message message = new Message();
        if (userId.equals(null) || semester.trim().isEmpty() || semester.equals(null)){
            message.setCode(0);
            message.setMsg("请求参数不能为空");
        }else {
            UserGpa userGpa = userGpaService.findUserGpaByUserIdAndSemester(userId, semester);
            if (userGpa == null) {
                message.setCode(0);
                message.setMsg("不存在");
            } else {
                message.setMsg("ok");
                message.setCode(1);
                message.setData(userGpa);
                userGpa.getUserByUserId().setPassword(null);
            }
        }
        return ResponseEntity.ok(message);
    }

}
