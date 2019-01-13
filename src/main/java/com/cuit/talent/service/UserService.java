package com.cuit.talent.service;

import com.cuit.talent.model.QUser;
import com.cuit.talent.model.User;
import com.cuit.talent.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;


    private JPAQueryFactory queryFactory;


    @PostConstruct
    public void initFactory()
    {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAQueryFactory successfully");
    }

    public List<User> findUsers(){
        QUser qUsers =  QUser.user;
        List<User> users = userRepository.findAll();
        return users;
//        return queryFactory.selectFrom(qUsers).where(qUsers.id.eq(1)).fetchOne();
    }
    public User findUser(Integer id){
        QUser user = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(user.id.eq(id));
        User user1 = new User();
        user1 = userRepository.findOne(booleanBuilder).get();
        return user1;
    }
}
