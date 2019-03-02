package com.cuit.talent.service;

import com.cuit.talent.model.*;
import com.cuit.talent.repository.UserGradeRepository;
import com.cuit.talent.repository.UserRepository;
import com.cuit.talent.utils.JwtHelper;
import com.cuit.talent.utils.valueobj.Message;
import com.cuit.talent.utils.valueobj.Token;
import com.fasterxml.jackson.databind.JsonNode;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGradeRepository userGradeRepository;
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
    List<Integer> grades = null;

    public User findByStudentId(String studentId) {
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(user.studentId.eq(studentId));
        Optional<User> existUser = userRepository.findOne(booleanBuilder);
        if (existUser.equals(Optional.empty())) {
            return null;
        }
        return existUser.get();
    }

    public User findUser(Integer id) {
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(user.id.eq(id));
        Optional<User> existUser = userRepository.findOne(booleanBuilder);
        if (existUser.equals(Optional.empty())) {
            return null;
        }
        return existUser.get();
    }


    public Message ensureUser(String studentId, String password) {
        Message message = new Message();
        if (studentId.trim().isEmpty() || studentId.equals(null)
                || password.trim().isEmpty() || password.equals(null)) {
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
    @Transactional
    public Message createUser(ArrayList<Map<String, Object>> userList) {
        Message message = new Message();
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).forEach((k, v) -> {
                if (k.equals("学号")) studentId = (String) v;
                if (k.equals("姓名")) username = (String) v;
                if (k.equals("性别")) {
                    if (v.equals("男")) sex = "1";
                    if (v.equals("女")) sex = "0";
                }
                if (k.equals("班级")) grade = (String) v;
                if (k.equals("入学年份")) startDate = (String) v;
            });
            User user = new User();
            user.setUsername(username);
            user.setStudentId(studentId);
            user.setSex(Integer.parseInt(sex));
            user.setPassword(studentId);
            user.setStartDate(startDate);
            if (this.findByStudentId(studentId) != null) {
                message.setCode(0);
                message.setMsg("学号为" + studentId + "的学生已经录入过");
                return message;
            }
            Role role = new Role();
            role.setId(3);
            user.setRoleByRoleId(role);
            userRepository.saveAndFlush(user);
            Grade grade1 = gradeService.findByGrade(grade).get();
            userGradeService.createUserGrade(user, grade1);
        }
        message.setCode(1);
        message.setMsg("录入学生信息成功");
        return message;
    }

    public Message updateUserPassword(JsonNode jsonNode) {
        Message message = new Message();
        String userId = jsonNode.path("userId").textValue();
        if (userId == null || userId.trim().isEmpty()) {
            message.setMsg("用户id为空");
            message.setCode(0);
            return message;
        }
        String oldPassword = jsonNode.path("oldPassword").textValue();
        if (oldPassword.equals(null) || oldPassword.trim().isEmpty()) {
            message.setMsg("用户旧密码为空");
            message.setCode(0);
            return message;
        }
        String newPassword = jsonNode.path("newPassword").textValue();
        if (newPassword.equals(null) || newPassword.trim().isEmpty()) {
            message.setMsg("用户新密码为空");
            message.setCode(0);
            return message;
        }
        if (oldPassword.equals(newPassword)){
            message.setMsg("新密码与旧密码不能相同");
            message.setCode(0);
            return message;
        }
        User existsUser = findUser(Integer.parseInt(userId));
        if (existsUser == null) {
            message.setMsg("不存在该用户");
            message.setCode(0);
            return message;
        }
        if (!existsUser.getPassword().equals(oldPassword)) {
            message.setMsg("旧密码错误");
            message.setCode(0);
            return message;
        }
        existsUser.setPassword(newPassword);
        userRepository.saveAndFlush(existsUser);
        message.setCode(1);
        message.setMsg("更新密码成功");
        return message;
    }
    public Message teacherFindClass(String teacherId){
        Message message = new Message();
        try {
            Optional<User> teacher  = null;
            QUser qUser = QUser.user;
            BooleanBuilder booleanBuilder2 = new BooleanBuilder();
            booleanBuilder2.and(qUser.studentId.eq(teacherId));
            teacher = userRepository.findOne(booleanBuilder2);
            if(!teacher.isPresent()){
                message.setMsg("查找失败，没有该老师,ID错误");
                message.setCode(1);
                return message;
            }
            if(teacher.get().getRoleByRoleId().getId()!=2){
                message.setMsg("查找失败，该用户不是老师,ID错误");
                message.setCode(1);
                return message;
            }
            QUserGrade qUserGrade = QUserGrade.userGrade;
            BooleanBuilder booleanBuilder3 = new BooleanBuilder();
            booleanBuilder3.and(qUserGrade.userByUserId.eq(teacher.get()));

            List<UserGrade> userGrades = (List<UserGrade>) userGradeRepository.findAll(booleanBuilder3);
            Iterator userGradel = userGrades.iterator();
            Map<String,Object> map  =  new LinkedHashMap<>();
            int i = 1;
            while (userGradel.hasNext()){

                UserGrade userGrade = new UserGrade();
                userGrade = (UserGrade) userGradel.next();

                map.put("class"+i,userGrade);
                BooleanBuilder booleanBuilder4 = new BooleanBuilder();
                booleanBuilder4.and(qUserGrade.gradeByGradeId.eq(userGrade.getGradeByGradeId()));
                List<UserGrade> userGradeList  = (List<UserGrade>) userGradeRepository.findAll(booleanBuilder4);

                List<User> userList = new ArrayList<User>();
                Iterator userGrades1 = userGradeList.iterator();
                while (userGrades1.hasNext()){

                    UserGrade userGrade1 = new UserGrade();
                    userGrade1 = (UserGrade) userGrades1.next();
                    if(userGrade1.getUserByUserId().getRoleByRoleId().getId()==3){

                        userGrade1.getUserByUserId().setPassword("*****");
                        userList.add(userGrade1.getUserByUserId());
                    }

                }
                map.put("students"+i,userList);
                i++;
            }



            message.setMsg("查找成功");
            message.setCode(1);
            message.setData(map);
        }catch(Exception e){
            //添加失败
            message.setMsg("查找错误");
            message.setCode(0);
            message.setData("EOORR Course:/n"+e.toString());
        }
        return message;
    }




    @Transactional
	public Message createTeacher(ArrayList<Map<String, Object>> userList) {
        Message message = new Message();
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).forEach((k, v) -> {
                if (k.equals("账号")) studentId = (String) v;
                if (k.equals("姓名")) username = (String) v;
                if (k.equals("性别")) {
                    if (v.equals("男")) sex = "1";
                    if (v.equals("女")) sex = "0";
                }
                if (k.equals("grades")) grades = (List<Integer>) v;
            });
            User user = new User();
            user.setUsername(username);
            user.setStudentId(studentId);
            user.setSex(Integer.parseInt(sex));
            user.setPassword(studentId);
            if (this.findByStudentId(studentId) != null) {
                message.setCode(0);
                message.setMsg("帐号为" + studentId + "的老师已经录入过");
                return message;
            }
            Role role = new Role();
            role.setId(2);
            user.setRoleByRoleId(role);
            userRepository.saveAndFlush(user);
            grades.forEach(j -> {
                Grade grade1 = gradeService.findByGradeId(j).get();
                userGradeService.createUserGrade(user, grade1);
            });
        }
        message.setCode(1);
        message.setMsg("录入学生信息成功");
        return message;
    }
}
