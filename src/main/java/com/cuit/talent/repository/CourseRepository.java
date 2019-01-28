package com.cuit.talent.repository;

import com.cuit.talent.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends QuerydslPredicateExecutor<Course>, JpaRepository<Course,Integer> {

}
