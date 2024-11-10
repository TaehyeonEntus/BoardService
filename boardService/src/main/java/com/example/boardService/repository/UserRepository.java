package com.example.boardService.repository;

import com.example.boardService.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    public Long save(User user);
    public void delete(User user);
    public Long update(User newUser, User oldUser);
    public User findByUsername(String username);
    public List<User> findAll();
    Boolean isExistByUsername(String username);
}
