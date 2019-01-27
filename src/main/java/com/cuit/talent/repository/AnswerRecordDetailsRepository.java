package com.cuit.talent.repository;


import com.cuit.talent.model.AnswerRecordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRecordDetailsRepository extends QuerydslPredicateExecutor<AnswerRecordDetails>, JpaRepository<AnswerRecordDetails,Integer> {
}
