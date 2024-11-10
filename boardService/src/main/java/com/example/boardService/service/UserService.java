package com.example.boardService.service;

import com.example.boardService.domain.User;
import com.example.boardService.dto.AuthDTO;
import com.example.boardService.jwt.JWTUtil;
import com.example.boardService.repository.MemoryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MemoryUserRepository memoryUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;

    public User findByUsername(String username) {
        return memoryUserRepository.findByUsername(username);
    }

    public ResponseEntity<String> join(AuthDTO authDTO) {
        String username = authDTO.getUsername();
        String password = authDTO.getPassword();

        Boolean isExist = memoryUserRepository.isExistByUsername(username);
        if (isExist) {
            System.out.println("이미 존재하는 아이디입니다.");
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole("ROLE_USER");

        memoryUserRepository.save(user);

        return ResponseEntity.ok("회원가입 성공");
    }

    public void login(){
        System.out.println("로그인");
    }
    public void logout() {
        System.out.println("로그아웃");
    }
}
