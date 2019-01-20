package com.cuit.talent.controller;

import com.cuit.talent.service.UserCourseService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserCourseController {
    @Autowired
    private UserCourseService userCourseService;

    @RequestMapping(value = "/api/userCourse/selectCourseByStudentId", method = RequestMethod.GET)
    public ResponseEntity selectCourseByStudentId(@RequestParam("studentId")String studentId){

        Message message = userCourseService.findUserCourseNameAndMarkByStudentId(studentId);

        return ResponseEntity.ok(message);
    }
    @RequestMapping(value = "/api/userCourse/addCourseMark", method = RequestMethod.POST)
    public ResponseEntity addUserCourse(@RequestBody JSONObject jsonObject){
        System.out.println("jsonobject:\n"+jsonObject.toString());
        Message message = userCourseService.addUserCourse(jsonObject);

        return  ResponseEntity.ok(message);
    }
}
