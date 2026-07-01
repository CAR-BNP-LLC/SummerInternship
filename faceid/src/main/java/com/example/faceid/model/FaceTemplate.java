package com.example.faceid.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FaceTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Име на човека, към когото е вързан този fingerprint (по избор)
    private String personName;

    // Fingerprint (масивът от числа), пазен като JSON текст
    @Lob
    @Column(nullable = false)
    private String fingerprintJson;

    private LocalDateTime createdAt;

    public FaceTemplate() {
    }

    public FaceTemplate(String personName, String fingerprintJson) {
        this.personName = personName;
        this.fingerprintJson = fingerprintJson;
        this.createdAt = LocalDateTime.now();
    }

    // Getters / setters

    public Long getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getFingerprintJson() {
        return fingerprintJson;
    }

    public void setFingerprintJson(String fingerprintJson) {
        this.fingerprintJson = fingerprintJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
