package com.cuit.talent.service;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.QGrade;
import com.cuit.talent.repository.GradeRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
