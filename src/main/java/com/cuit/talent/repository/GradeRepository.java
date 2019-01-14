package com.cuit.talent.repository;

import com.cuit.talent.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends QuerydslPredicateExecutor<Grade>,JpaRepository<Grade,Integer> {
}
