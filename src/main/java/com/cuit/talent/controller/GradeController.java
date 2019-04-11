package com.cuit.talent.controller;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.UserGrade;
import com.cuit.talent.service.GradeService;
import com.cuit.talent.service.UserGradeService;
import com.cuit.talent.utils.valueobj.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private UserGradeService userGradeService;

    @RequestMapping(value = "/api/find/grades", method = RequestMethod.GET)
    public ResponseEntity findAllGrade(){
        List<Grade> grades = gradeService.findAll();
        return ResponseEntity.ok(grades);
    }

    @RequestMapping(value = "/api/find/teacherGrades", method = RequestMethod.GET)
    public ResponseEntity findGradesByTeacherId(Integer userId){
        List<UserGrade> lists = userGradeService.findByUserId(userId);
        Message message = gradeService.findByGradeIds(lists);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "api/grade/gpa", method = RequestMethod.GET)
    public ResponseEntity findGradeGpa(Integer gradeId){
        Message message = new Message();
        if (gradeId == null){
            message.setCode(0);
            message.setMsg("gradeId不能为空");
        }else {
            message = gradeService.findGradeGpa(gradeId);
            message.setCode(1);
            message.setMsg("获取所有绩点成功");

        }
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "api/grade/findGradeStudents", method = RequestMethod.GET)
    public ResponseEntity findGradeStudents(Integer gradeId){
        Message message = new Message();
        if (gradeId == null){
            message.setCode(0);
            message.setMsg("gradeId不能为空");
        }else {
            message.setData( userGradeService.findStudentsByGradeId(gradeId));
            message.setCode(1);
            message.setMsg("获取班级下所有学生成功");

        }
        return ResponseEntity.ok(message);
    }
}
