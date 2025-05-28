package com.example.test2.repository;

import com.example.test2.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<Board, Integer>, QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {
}
