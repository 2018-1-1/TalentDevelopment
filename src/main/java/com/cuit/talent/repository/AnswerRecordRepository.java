package com.cuit.talent.repository;

import com.cuit.talent.model.AnswerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRecordRepository extends QuerydslPredicateExecutor<AnswerRecord>, JpaRepository<AnswerRecord,Integer> {
}
