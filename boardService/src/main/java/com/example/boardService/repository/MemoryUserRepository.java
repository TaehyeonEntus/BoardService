package com.example.boardService.repository;

import com.example.boardService.domain.Board;
import com.example.boardService.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static List<User> userList = new ArrayList<>();
    private static Long userId = 1L;

    @Override
    public Long save(User user) {
        user.setUserId(userId);
        userList.add(user);
        userId++;
        return user.getUserId();
    }

    @Override
    public void delete(User user) {
        userList.remove(user);
    }

    @Override
    public Long update(User newUser, User oldUser) {
        int index = userList.indexOf(oldUser);
        userList.set(index, newUser);
        return newUser.getUserId();
    }

    @Override
    public User findByUsername(String username) {
        return userList.stream()
                .filter(item -> item.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public Boolean isExistByUsername(String username) {
        return userList.stream()
                .anyMatch(item -> item.getUsername().equals(username));
    }
}
