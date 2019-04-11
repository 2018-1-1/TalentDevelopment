package com.cuit.talent.service;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.QUserGrade;
import com.cuit.talent.model.User;
import com.cuit.talent.model.UserGrade;
import com.cuit.talent.repository.UserGradeRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    public List<UserGrade> findByUserId(Integer userId){
        QUserGrade qUserGrade = QUserGrade.userGrade;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserGrade.userByUserId.id.eq(userId));
        List<UserGrade> userGrades =  Lists.newArrayList(userGradeRepository.findAll(booleanBuilder));
        return userGrades;
    }
    public UserGrade findUserGradeByUserIdAndGradeId(Integer userId, Integer gradeId){
        QUserGrade qUserGrade = QUserGrade.userGrade;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserGrade.userByUserId.id.eq(userId));
        booleanBuilder.and(qUserGrade.gradeByGradeId.id.eq(gradeId));
        Optional<UserGrade> existUserGrade = userGradeRepository.findOne(booleanBuilder);
        if (existUserGrade.equals(Optional.empty())) {
            return null;
        }
        return existUserGrade.get();
    }
    public List<UserGrade> findByGradeId(Integer gradeId){
        QUserGrade qUserGrade = QUserGrade.userGrade;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserGrade.gradeByGradeId.id.eq(gradeId));
        List<UserGrade> userGrades =  Lists.newArrayList(userGradeRepository.findAll(booleanBuilder));
        return userGrades;
    }

    public List<UserGrade> findStudentsByGradeId(Integer gradeId){
        QUserGrade qUserGrade = QUserGrade.userGrade;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserGrade.gradeByGradeId.id.eq(gradeId));
        booleanBuilder.and(qUserGrade.userByUserId.roleByRoleId.id.eq(3));
        List<UserGrade> userGrades =  Lists.newArrayList(userGradeRepository.findAll(booleanBuilder));
        for(UserGrade userGrade :userGrades){
            userGrade.getUserByUserId().setPassword(null);
        }
        return userGrades;
    }
}
