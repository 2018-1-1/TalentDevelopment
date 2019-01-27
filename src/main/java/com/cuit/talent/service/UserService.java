package com.cuit.talent.service;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.QUser;
import com.cuit.talent.model.Role;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.UserRepository;
import com.cuit.talent.utils.JwtHelper;
import com.cuit.talent.utils.valueobj.Message;
import com.cuit.talent.utils.valueobj.Token;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private Token token;

    @Autowired
    private UserGradeService userGradeService;

    @Autowired
    private GradeService gradeService;

    String username = null;
    String password = null;
    String sex = null;
    String studentId = null;
    String grade = null;
    String startDate = null;
    public User findByStudentId(String studentId){
       QUser user = QUser.user;
       BooleanBuilder booleanBuilder = new BooleanBuilder();
       booleanBuilder.and(user.studentId.eq(studentId));
       Optional<User> existUser = userRepository.findOne(booleanBuilder);
       if (existUser.equals(Optional.empty())){
           return null;
       }
       return existUser.get();
    }

    public User findUser(Integer id){
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(user.id.eq(id));
        Optional<User> existUser = userRepository.findOne(booleanBuilder);
        if (existUser.equals(Optional.empty())){
            return null;
        }
        return existUser.get();
    }

    public Message ensureUser(String studentId, String password){
        Message message = new Message();
        if (studentId.trim().isEmpty() || studentId.equals(null)
                || password.trim().isEmpty() || password.equals(null)){
            message.setCode(0);
            message.setMsg("密码或学号为空");
            return message;
        }
        User existUser = this.findByStudentId(studentId);
        if (existUser == null) {
            message.setCode(0);
            message.setMsg("不存在该用户");
            return message;
        }
        if (!existUser.getPassword().equals(password)) {
            message.setCode(0);
            message.setMsg("密码错误");
            return message;
        } else {
            Map<String, Object> map = new HashMap<>();
            Integer roleId = existUser.getRoleByRoleId().getId();
            Integer userId = existUser.getId();
            token.setToken(jwtHelper.createToken(userId, roleId));
            map.put("userId", userId);
            map.put("roleId", roleId);
            map.put("token", token);
            message.setCode(1);
            message.setMsg("登录成功");
            message.setData(map);
            return message;
        }
    }

    public Message createUser(ArrayList<Map<String, Object>> userList){
        Message message = new Message();
        for (int i = 0; i <userList.size();i++){
            System.out.println(i);
            userList.get(i).forEach((k,v)->{
                if (k.equals("学号")) studentId = (String) v;
                if (k.equals("姓名")) username = (String) v;
                if (k.equals("性别")){
                    if (v.equals("男")) sex = "1";
                    if (v.equals("女")) sex = "0" ;
                }
                if (k.equals("班级")) grade = (String) v;
                if (k.equals("入学年份")) startDate = (String) v;
            });
            User user = new User();
            user.setUsername(username);
            user.setStudentId(studentId);
            user.setSex(Integer.parseInt(sex));
            user.setPassword(studentId);
            user.setStartDate(Date.valueOf(startDate));
            if (this.findByStudentId(studentId) != null){
                 message.setCode(0);
                 message.setMsg("学号为"+studentId+"的学生已经录入过");
                 return message;
            }
            Role role = new Role();
            role.setId(3);
            user.setRoleByRoleId(role);
            userRepository.saveAndFlush(user);
            Grade grade1 =  gradeService.findByGrade(grade).get();
            userGradeService.createUserGrade(user, grade1);
        }
        message.setCode(1);
        message.setMsg("录入学生信息成功");
        return message;
    }
}
