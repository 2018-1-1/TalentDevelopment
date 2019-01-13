package com.cuit.talent.service;

import com.cuit.talent.model.QUser;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String studentId){
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

}
