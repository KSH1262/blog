package com.example.test2.dto;

import com.example.test2.entity.Board;

public class BoardResponseDto {
    private Long id;
    private String title;
    private String contentPreview;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contentPreview = board.getContentPreview();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContentPreview() { return contentPreview; }

    public static BoardResponseDto fromEntity(Board board) {
        return new BoardResponseDto(board);
    }
}
