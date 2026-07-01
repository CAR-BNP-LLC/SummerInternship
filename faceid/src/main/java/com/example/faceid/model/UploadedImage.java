package com.example.faceid.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UploadedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;      // име в uploads/
    private String originalName;  // оригинално име
    private LocalDateTime uploadedAt;

    public UploadedImage() {
    }

    public UploadedImage(String fileName, String originalName) {
        this.fileName = fileName;
        this.originalName = originalName;
        this.uploadedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
