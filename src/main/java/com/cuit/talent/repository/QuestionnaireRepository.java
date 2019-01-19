package com.cuit.talent.repository;


import com.cuit.talent.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends QuerydslPredicateExecutor<Questionnaire>, JpaRepository<Questionnaire,Integer> {
}
