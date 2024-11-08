package com.example.boardService.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class Board {
    private Long boardId;
    private String title;
    private String content;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
