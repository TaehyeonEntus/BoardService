package com.example.boardService.domain;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String username;
    private String password;

    private String role;
}
