package com.example.faceid.repository;

import com.example.faceid.model.FaceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaceTemplateRepository extends JpaRepository<FaceTemplate, Long> {
    Optional<FaceTemplate> findByFaceId(String faceId);
}
