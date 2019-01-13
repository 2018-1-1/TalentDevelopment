package com.cuit.talent.service;

import com.cuit.talent.model.QUser;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.UserRepository;
import com.cuit.talent.utils.JwtHelper;
import com.cuit.talent.utils.valueobj.Message;
import com.cuit.talent.utils.valueobj.Token;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private Token token;

    public User findByStudentId(String studentId){
       QUser user = QUser.user;
       BooleanBuilder booleanBuilder = new BooleanBuilder();
       booleanBuilder.and(user.studentId.eq(studentId));
       Optional<User> existUser = userRepository.findOne(booleanBuilder);
       return existUser.get();
    }

    public User findUser(Integer id){
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(user.id.eq(id));
        User existUser  = userRepository.findOne(booleanBuilder).get();
        return existUser;
    }

    public Message ensureUser(String studentId, String password){
        Message message = new Message();
        if (studentId.trim().isEmpty() || studentId.equals(null)
                || password.trim().isEmpty() || password.equals(null)){
            message.setCode(0);
            message.setMsg("密码或学号为空");
        }else {
            try {
                User existUser = this.findByStudentId(studentId);
                if (existUser == null) {
                    message.setCode(0);
                    message.setMsg("不存在该用户");
                } else if (!existUser.getPassword().equals(password)) {
                    message.setCode(0);
                    message.setMsg("密码错误");
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
                }
            }catch (Exception e){
                message.setMsg("不存在该用户");
                message.setCode(0);
                return message;
            }
        }
        return  message;
    }
}
