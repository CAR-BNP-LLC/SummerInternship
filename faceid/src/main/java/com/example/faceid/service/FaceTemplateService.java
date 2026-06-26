package com.example.faceid.service;

import com.example.faceid.model.FaceTemplate;
import com.example.faceid.repository.FaceTemplateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class FaceTemplateService {

    private final FaceDetectionService faceDetectionService;
    private final FaceTemplateRepository faceTemplateRepository;

    public FaceTemplateService(FaceDetectionService faceDetectionService,
                               FaceTemplateRepository faceTemplateRepository) {
        this.faceDetectionService = faceDetectionService;
        this.faceTemplateRepository = faceTemplateRepository;
    }

    /**
     * Взима fingerprint от локалната снимка (defaultImagePath) и го записва в БД.
     * Ако FaceDetectionService хвърли Exception, го обръщаме в RuntimeException.
     */
    public FaceTemplate createTemplateFromSavedImage(String personName) {
        JsonNode fingerprintJson;
        try {
            fingerprintJson = faceDetectionService.getFirstFaceFingerprint();
        } catch (Exception e) {
            throw new RuntimeException("Error getting face fingerprint from model", e);
        }

        boolean hasFace = fingerprintJson.path("hasFace").asBoolean(false);
        if (!hasFace) {
            throw new IllegalStateException("Не е открито лице в снимката.");
        }

        String faceId = fingerprintJson.path("faceId").asText(null);
        if (faceId == null) {
            throw new IllegalStateException("Face ID липсва в отговора.");
        }

        // Проверка дали вече имаме такъв faceId
        Optional<FaceTemplate> existing = faceTemplateRepository.findByFaceId(faceId);
        if (existing.isPresent()) {
            return existing.get();
        }

        FaceTemplate template = new FaceTemplate(personName, faceId);
        return faceTemplateRepository.save(template);
    }
}
