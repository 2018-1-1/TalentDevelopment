package com.cuit.talent.service;

import com.cuit.talent.model.*;
import com.cuit.talent.repository.CourseRepository;
import com.cuit.talent.repository.UserCourseRepository;
import com.cuit.talent.repository.UserRepository;
import com.cuit.talent.utils.valueobj.Message;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserCourseService {
    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    public Message findUserCourseNameAndMarkByStudentId(String studentId){
        Message message = new Message();
        try {
            Map<String, Object> map = new HashMap<>();

            QUserCourse userCourse  = QUserCourse.userCourse;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(userCourse.userByUserId.studentId.eq(studentId));
            List<UserCourse> userCourseList = (List<UserCourse>) userCourseRepository.findAll(booleanBuilder);

            Iterator it = userCourseList.iterator();
            while(it.hasNext()) {
                UserCourse userCourse1 = (UserCourse) it.next();
                map.put(userCourse1.getCourseByCourseId().getCourseName(),userCourse1.getMark());
            }

            message.setMsg("返回成功");
            message.setCode(1);
            message.setData(map);
        }catch (Exception e){
            message.setMsg("返回失败");
            message.setCode(0);
        }
        return message;
    }
    //从JSONObject中得到学生user和对应的课程course，将课程的分数记录到mark
    public Message addUserCourse(JSONObject jsonObject){
        Message message = new Message();
        try{
            Iterator iterator = jsonObject.keys();
            while(iterator.hasNext()){
                //初始化UserCourse的属性
                Optional<Course> course = null;
                Optional<User> user = null;
                int mark = 0;

                JSONObject jsonObject1 = jsonObject.optJSONObject(iterator.next().toString());
                Iterator iterator1 = jsonObject1.keys();

                while (iterator1.hasNext()){//查找学号对应的学生
                    String key = iterator1.next().toString();
                    if(key.equals("学号")){
                        String valueStudentid = jsonObject1.getString(key);
                        QUser qUser  = QUser.user;//学生
                        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
                        booleanBuilder1.and(qUser.studentId.eq(valueStudentid));
                        user = userRepository.findOne(booleanBuilder1);
                        break;
                    }
                }
                //其他的通过名称去课程表去查相应的课程名，有就保存课程和分数给学生到usercourse表
                while (iterator1.hasNext()) {
                    String keyCourseName = iterator1.next().toString();
                    String valueCourseMark = jsonObject1.getString(keyCourseName);

                    QCourse qCourse = QCourse.course;//课程
                    BooleanBuilder booleanBuilder2 = new BooleanBuilder();
                    booleanBuilder2.and(qCourse.courseName.eq(keyCourseName));
                    course = courseRepository.findOne(booleanBuilder2);

                    mark = Integer.parseInt(valueCourseMark);//分数
                    //保存UserCourse
                    UserCourse userCourse = new UserCourse();
                    userCourse.setCourseByCourseId(course.get());
                    userCourse.setMark(mark);
                    userCourse.setUserByUserId(user.get());
                    userCourseRepository.save(userCourse);
                    //添加成功
                }
            }
            message.setMsg("添加成功");
            message.setCode(1);
        }catch (Exception e){
            //添加失败
            message.setMsg("添加失败");
            message.setCode(0);
        }
        return message;
    }
}
