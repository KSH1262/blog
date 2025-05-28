package com.example.test2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequestDto {
    private long userId;
    private int boardId;
    private String content;
}
