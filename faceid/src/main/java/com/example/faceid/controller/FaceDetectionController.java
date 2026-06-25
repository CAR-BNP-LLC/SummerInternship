package com.example.faceid.controller;

import com.example.faceid.service.FaceDetectionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/face")
@CrossOrigin(origins = "*")
public class FaceDetectionController {

    private final FaceDetectionService faceDetectionService;

    public FaceDetectionController(FaceDetectionService faceDetectionService) {
        this.faceDetectionService = faceDetectionService;
    }

    /**
     * GET /api/face/fingerprint
     * Чете предварително запазена снимка в resources/images,
     * открива дали има лице и връща fingerprint (faceId) + bounding box.
     */
    @GetMapping("/fingerprint")
    public ResponseEntity<?> getFaceFingerprint() {
        try {
            JsonNode fingerprint = faceDetectionService.getFirstFaceFingerprint();
            return ResponseEntity.ok(fingerprint);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error detecting face: " + e.getMessage());
        }
    }
}
