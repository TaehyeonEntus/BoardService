package com.example.boardService.controller;

import com.example.boardService.dto.AuthDTO;
import com.example.boardService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("join")
    public String join() {
        return "join";
    }

    @PostMapping("/join")
    public String join(AuthDTO authDTO) {
        userService.join(authDTO);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login() {
        userService.login();
        return "redirect:/";
    }
}
