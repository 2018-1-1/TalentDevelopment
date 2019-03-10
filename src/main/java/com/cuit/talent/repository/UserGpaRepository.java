package com.cuit.talent.repository;

import com.cuit.talent.model.UserGpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGpaRepository extends QuerydslPredicateExecutor<UserGpa>,JpaRepository<UserGpa,Integer> {
}
