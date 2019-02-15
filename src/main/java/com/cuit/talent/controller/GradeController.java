package com.cuit.talent.controller;

import com.cuit.talent.model.Grade;
import com.cuit.talent.service.GradeService;
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

    @RequestMapping(value = "/api/find/grades", method = RequestMethod.GET)
    public ResponseEntity findAllGrade(){
        List<Grade> grades = gradeService.findAll();
        return ResponseEntity.ok(grades);
    }

}
