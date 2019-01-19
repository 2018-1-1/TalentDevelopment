package com.cuit.talent.repository;

import com.cuit.talent.model.QuestionnaireIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireIssueRepository extends QuerydslPredicateExecutor<QuestionnaireIssue>, JpaRepository<QuestionnaireIssue,Integer> {
}
