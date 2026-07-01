package com.example.faceid.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user") // за да не се бие с system user в Postgres
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;   // име за логин

    @Column(nullable = false)
    private String password;   // за учебен проект ще е plain-text (в реалност трябва hash)

    private String role;       // по избор: "USER", "ADMIN" и т.н.

    public AppUser() {
    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters/setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
