package com.example.faceid.service;

import com.example.faceid.model.AppUser;
import com.example.faceid.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;

    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Регистрация: създава нов потребител
    public AppUser register(String username, String password, String role) {
        // Проверка дали вече има такъв username
        Optional<AppUser> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        AppUser user = new AppUser(username, password, role);
        return userRepository.save(user);
    }

    // Логин: проверява username + password
    public AppUser login(String username, String password) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return user;
    }
}
