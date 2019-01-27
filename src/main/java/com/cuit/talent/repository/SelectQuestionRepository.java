package com.cuit.talent.repository;

import com.cuit.talent.model.SelectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface SelectQuestionRepository extends QuerydslPredicateExecutor<SelectQuestion>, JpaRepository<SelectQuestion,Integer> {
}
