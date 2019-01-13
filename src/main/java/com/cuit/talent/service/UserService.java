package com.cuit.talent.service;

import com.cuit.talent.model.QUser;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.UserRepository;
import com.cuit.talent.utils.valueobj.Message;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByStudentId(String studentId){
       QUser user = QUser.user;
       BooleanBuilder booleanBuilder = new BooleanBuilder();
       booleanBuilder.and(user.studentId.eq(studentId));
       User existUser = userRepository.findOne(booleanBuilder).get();
       return existUser;
    }

    public User findUser(Integer id){
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(user.id.eq(id));
        User existUser  = userRepository.findOne(booleanBuilder).get();
        return existUser;
    }

    public Message ensureUser(String studentId, String password) throws Exception {
        Message message = new Message();
        if (studentId.trim().isEmpty() || studentId.equals(null)
                || password.trim().isEmpty() || password.equals(null)){
            message.setCode(0);
            message.setMsg("密码或学号为空");
        }
        User existUser = this.findByStudentId(studentId);
        if (existUser == null){
            message.setCode(0);
            message.setMsg("不存在该用户");
        }else if (!existUser.getPassword().equals(password)){
            message.setCode(0);
            message.setMsg("密码错误");
        }

        return  null;

    }
}
