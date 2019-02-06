package com.cuit.talent.service;


import com.cuit.talent.model.*;
import com.cuit.talent.repository.CourseRepository;
import com.cuit.talent.repository.UserCourseRepository;
import com.cuit.talent.repository.UserRepository;
import com.cuit.talent.utils.valueobj.Message;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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

            List<UserCourseSelect> userCourseSelectList = new ArrayList<UserCourseSelect>();
            QUserCourse userCourse  = QUserCourse.userCourse;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(userCourse.userByUserId.studentId.eq(studentId));
            List<UserCourse> userCourseList = (List<UserCourse>) userCourseRepository.findAll(booleanBuilder);

            Iterator it = userCourseList.iterator();
            while(it.hasNext()) {
                UserCourse userCourse1 = (UserCourse) it.next();

                UserCourseSelect userCourseSelect = new UserCourseSelect();
                userCourseSelect.setCourseName(userCourse1.getCourseByCourseId().getCourseName());
                userCourseSelect.setGrade(userCourse1.getMark());

                userCourseSelectList.add(userCourseSelect);


            }

            message.setMsg("返回成功");
            message.setCode(1);
            message.setData(userCourseSelectList);
        }catch (Exception e){
            message.setMsg("返回失败");
            message.setCode(0);
        }
        return message;
    }
    //从JSONObject中得到学生user和对应的课程course，将课程的分数记录到mark
    public Message addUserCourse(ArrayList<Map<String, Object>> userCourseList){
        Message message = new Message();
        boolean userfind = false;
        int mark = 0;
        try{

            for(int i = 0 ;i<userCourseList.size();i++){
                Optional<User> user = null;
                for (Map.Entry<String, Object> entry : userCourseList.get(i).entrySet()) {
                    String key = entry.getKey();
                    System.out.println("first key"+i+":"+entry.getKey());
                   
                    if (key.equals("学号")) {
                        String valueStudentid = (String) entry.getValue();
                        System.out.println(valueStudentid+"---valueStudentid");
                        QUser qUser = QUser.user;//查找学生
                        BooleanBuilder booleanBuilder1 = new BooleanBuilder();
                        booleanBuilder1.and(qUser.studentId.eq(valueStudentid));
                        user = userRepository.findOne(booleanBuilder1);
                        System.out.println("-a-a-a-a-:"+userRepository.findOne(booleanBuilder1).get().getUsername());
                        //如有用户则添加，反之不添加信息；
                        if(!user.isPresent()){
                            System.out.println("没有该用户"+i+":");
                            userfind=true;
                        }else {
                            System.out.println("user:"+user.get().getUsername());
                        }
                        break;
                    }
                }

                if(!userfind){
                    for (Map.Entry<String, Object> entry : userCourseList.get(i).entrySet()) {
                        Optional<Course> course = null;
                        String keyCourseName = entry.getKey();
                        System.out.println("keyandvalue:"+entry.getKey()+","+entry.getValue().toString());
                        QCourse qCourse = QCourse.course;//课程
                        BooleanBuilder booleanBuilder2 = new BooleanBuilder();
                        booleanBuilder2.and(qCourse.courseName.eq(keyCourseName));
                        course = courseRepository.findOne(booleanBuilder2);
                        //如果查到找该课程：添加；
                        if(course.isPresent()){
                            String valueCourseMark = (String) entry.getValue();

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
