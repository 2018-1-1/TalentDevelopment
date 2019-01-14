package com.cuit.talent.service;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.User;
import com.cuit.talent.model.UserGrade;
import com.cuit.talent.repository.UserGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserGradeService {
    @Autowired
    private UserGradeRepository userGradeRepository;
    @Autowired
    private EntityManager entityManager;
    public void createUserGrade(User user, Grade grade){
        UserGrade userGrade = new UserGrade();
        userGrade.setGradeByGradeId(grade);
        userGrade.setUserByUserId(user);
        userGradeRepository.saveAndFlush(userGrade);
        entityManager.clear();
    }

}
