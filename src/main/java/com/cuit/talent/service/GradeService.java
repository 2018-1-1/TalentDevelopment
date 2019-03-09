package com.cuit.talent.service;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.QGrade;
import com.cuit.talent.model.UserGrade;
import com.cuit.talent.repository.GradeRepository;
import com.cuit.talent.utils.valueobj.Message;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> findAll(){
        List<Grade> grades = gradeRepository.findAll();
        return grades;
    }

    public Optional<Grade> findByGrade(String grade){
        QGrade qGrade = QGrade.grade1;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qGrade.grade.eq(grade));
        return gradeRepository.findOne(booleanBuilder);
    }

    public Optional<Grade> findByGradeId(Integer id){
        QGrade qGrade = QGrade.grade1;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qGrade.id.eq(id));
        return gradeRepository.findOne(booleanBuilder);
    }

    public Message findByGradeIds(List<UserGrade> userGrades){
        List<Grade> grades = new ArrayList<>();
        Message message = new Message();
        try{
            userGrades.forEach(i-> grades.add(findByGradeId(i.getGradeByGradeId().getId()).get()));
        }catch (Exception e){
            message.setCode(0);
            message.setMsg("查找失败，不存在对应班级");
        }
        message.setCode(1);
        message.setData(grades);
        message.setMsg("查找成功");
        return message;
    }
}
