package com.example.faceid.controller;

import com.example.faceid.User;
import com.example.faceid.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // GET /users -> връща всички записи от таблица users
    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    // POST /users с JSON { "name": "Ivan" } -> създава нов запис
    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }
}
