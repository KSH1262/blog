package com.example.test2.repository;

import com.example.test2.dto.BoardSearchDto;
import com.example.test2.entity.Board;
import com.example.test2.entity.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public BoardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Board> searchByTitle(BoardSearchDto searchDto, Pageable pageable) {
        QBoard board = QBoard.board;

        BooleanExpression condition = null;
        if (searchDto.getKeyword() != null && !searchDto.getKeyword().isEmpty()) {
            condition = board.title.containsIgnoreCase(searchDto.getKeyword());
        }

        List<Board> content = queryFactory
                .selectFrom(board)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.id.desc())
                .fetch();

        long total = queryFactory
                .select(board.count())
                .from(board)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}