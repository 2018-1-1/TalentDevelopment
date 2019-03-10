package com.cuit.talent.service;

import com.cuit.talent.model.*;
import com.cuit.talent.repository.UserGpaRepository;
import com.cuit.talent.utils.valueobj.Message;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserGpaService {
    @Autowired
    private UserGpaRepository userGpaRepository;
    @Autowired
    private UserGradeService userGradeService;
    @Autowired
    private UserService userService;
    @Autowired
    private GradeService gradeService;
    String  semester = null;
    double gpa = 0;
    String studentId = null;
    String grade = null;
    @Transactional
    public Message createUserGpa(ArrayList<Map<String, Object>> userGpaList) {
        Message message = new Message();
        if (userGpaList.size() == 0){
            message.setCode(0);
            message.setMsg("录入数据不能为空");
            return message;
        }
        for (int i = 0; i < userGpaList.size(); i++) {
            userGpaList.get(i).forEach((k, v) -> {
                if (k.equals("学期")) semester = (String) v;
                if (k.equals("绩点")) gpa = (double) v;
                if (k.equals("班级")) grade = (String) v;
                if (k.equals("学号")) studentId = (String) v;
            });
            if (studentId == null){
                message.setCode(0);
                message.setMsg("学号不能为空");
                return message;
            }
            if (semester == null){
                message.setCode(0);
                message.setMsg("学期不能为空");
                return message;
            }
            if (grade == null){
                message.setCode(0);
                message.setMsg("班级不能为空");
                return message;
            }
            User existUser = userService.findByStudentId(studentId);
            if (existUser == null) {
                message.setCode(0);
                message.setMsg("学号为" + studentId + "的学生不存在");
                return message;
            }
            Grade existGrade = gradeService.findByGrade(grade);
            if (existGrade == null){
                message.setCode(0);
                message.setMsg("班级"+grade+"不存在");
                return message;
            }
            UserGrade userGrade = userGradeService.findUserGradeByUserIdAndGradeId(existUser.getId(), existGrade.getId());
            if (userGrade == null){
                message.setCode(0);
                message.setMsg("学号为"+studentId+"的学生与班级"+grade+"不对应");
                return message;
            }
            UserGpa existUserGpa = this.findUserGpaByUserIdAndSemester(existUser.getId(), semester);
            if (existUserGpa != null){
                message.setCode(0);
                message.setMsg("学号为"+studentId+"的学生第"+semester+"学期已经录入过");
                return message;
            }
            UserGpa userGpa = new UserGpa();
            userGpa.setGpa(gpa);
            userGpa.setSemester(semester);
            userGpa.setUserByUserId(existUser);
            userGpaRepository.save(userGpa);
            grade = null;
            gpa = 0;
            semester = null;
            studentId = null;
        }
        message.setCode(1);
        message.setMsg("录入学生绩点成功");
        return message;
    }

    public UserGpa findUserGpaByUserIdAndSemester(Integer userId, String semester){
        QUserGpa qUserGpa = QUserGpa.userGpa;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserGpa.userByUserId.id.eq(userId));
        booleanBuilder.and(qUserGpa.semester.eq(semester));
        Optional<UserGpa> existUserGpa = userGpaRepository.findOne(booleanBuilder);
        if (existUserGpa.equals(Optional.empty())){
            return null;
        }
        return existUserGpa.get();
    }

    public List<UserGpa> findUserGpasByUserId(Integer userId){
        QUserGpa qUserGpa = QUserGpa.userGpa;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserGpa.userByUserId.id.eq(userId));
        List<UserGpa> userGpaList = (List<UserGpa>) userGpaRepository.findAll(booleanBuilder);
        return userGpaList;
    }
}
