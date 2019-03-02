package com.cuit.talent.controller;

import com.cuit.talent.service.UserCourseService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class UserCourseController {
    @Autowired
    private UserCourseService userCourseService;

    @RequestMapping(value = "/api/userCourse/selectCourseByStudentId", method = RequestMethod.GET)
    public ResponseEntity selectCourseByStudentId(@RequestParam("studentId")Integer studentId){
        Message message = userCourseService.findUserCourseNameAndMarkByStudentId(studentId);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/userCourse/addCourseMark", method = RequestMethod.POST)
    public ResponseEntity addUserCourse(@RequestBody JSONObject jsonObject){
        System.out.print(jsonObject);
        ArrayList<Map<String, Object>> userCourseList = (ArrayList) jsonObject.get("userCourse");
        Message message = userCourseService.addUserCourse(userCourseList);
        return  ResponseEntity.ok(message);
    }

}
