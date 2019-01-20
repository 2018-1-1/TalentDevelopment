package com.cuit.talent.service;

import com.cuit.talent.model.Course;
import com.cuit.talent.model.QCourse;
import com.cuit.talent.repository.CourseRepository;
import com.cuit.talent.utils.JwtHelper;
import com.cuit.talent.utils.valueobj.Token;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private Token token;

    public Course findByCourseId(Integer courseId){
        QCourse course  = QCourse.course;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(course.id.eq(courseId));
        Optional<Course> exitCourse = courseRepository.findOne(booleanBuilder);
        return exitCourse.get();
    }
}
