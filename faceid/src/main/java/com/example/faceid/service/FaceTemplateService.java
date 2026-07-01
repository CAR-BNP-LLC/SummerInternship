package com.example.faceid.service;

import com.example.faceid.model.FaceTemplate;
import com.example.faceid.repository.FaceTemplateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class FaceTemplateService {

    private final FaceDetectionService faceDetectionService;
    private final FaceTemplateRepository faceTemplateRepository;

    public FaceTemplateService(FaceDetectionService faceDetectionService,
                               FaceTemplateRepository faceTemplateRepository) {
        this.faceDetectionService = faceDetectionService;
        this.faceTemplateRepository = faceTemplateRepository;
    }

    public FaceTemplate createTemplateFromSavedImage(String personName) {
        // 1) Взимаме JSON от модела
        JsonNode result;
        try {
            result = faceDetectionService.getFirstFaceFingerprint();
        } catch (Exception e) {
            throw new RuntimeException("Error getting face fingerprint from model", e);
        }

        // 2) Проверяваме има ли лице
        boolean hasFace = result.path("hasFace").asBoolean(false);
        if (!hasFace) {
            throw new IllegalStateException("Не е открито лице в снимката.");
        }

        // 3) Взимаме fingerprint масива
        JsonNode fingerprintNode = result.path("fingerprint");
        if (fingerprintNode.isMissingNode() || !fingerprintNode.isArray()) {
            throw new IllegalStateException("Fingerprint липсва или не е масив в отговора.");
        }

        // 4) Сериализираме fingerprint масива до String (JSON текст)
        String fingerprintJsonString = fingerprintNode.toString();

        // 5) Създаваме и записваме шаблон
        FaceTemplate template = new FaceTemplate(personName, fingerprintJsonString);
        return faceTemplateRepository.save(template);
    }
}
