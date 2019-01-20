package com.cuit.talent.repository;


import com.cuit.talent.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends QuerydslPredicateExecutor<QuestionType>, JpaRepository<QuestionType,Integer> {
}
