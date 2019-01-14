package com.cuit.talent.repository;

import com.cuit.talent.model.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGradeRepository extends QuerydslPredicateExecutor<UserGrade>,JpaRepository<UserGrade,Integer> {
}
