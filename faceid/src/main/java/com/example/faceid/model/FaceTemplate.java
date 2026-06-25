package com.example.faceid.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FaceTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // по желание – име на човека, към когото е вързан fingerprint-а
    private String personName;

    // fingerprint от Face API (например faceId)
    @Column(nullable = false, unique = true)
    private String faceId;

    private LocalDateTime createdAt;

    public FaceTemplate() {
    }

    public FaceTemplate(String personName, String faceId) {
        this.personName = personName;
        this.faceId = faceId;
        this.createdAt = LocalDateTime.now();
    }

    // getters и setters

    public Long getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
