package com.cuit.talent.repository;


import com.cuit.talent.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends QuerydslPredicateExecutor<UserCourse>, JpaRepository<UserCourse,Integer> {


}
