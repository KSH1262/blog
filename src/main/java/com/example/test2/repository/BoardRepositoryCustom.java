package com.example.test2.repository;

import com.example.test2.dto.BoardSearchDto;
import com.example.test2.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> searchByTitle(BoardSearchDto searchDto, Pageable pageable);
}
