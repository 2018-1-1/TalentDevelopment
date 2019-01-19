package com.cuit.talent.repository;

import com.cuit.talent.model.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends QuerydslPredicateExecutor<QuestionAnswer>, JpaRepository<QuestionAnswer,Integer> {
}
