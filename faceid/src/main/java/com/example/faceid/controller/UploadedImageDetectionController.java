package com.example.faceid.controller;

import com.example.faceid.service.FaceDetectionService;
import com.example.faceid.service.FileStorageService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/face")
@CrossOrigin(origins = "*")
public class UploadedImageDetectionController {

    private final FaceDetectionService faceDetectionService;
    private final FileStorageService fileStorageService;

    public UploadedImageDetectionController(FaceDetectionService faceDetectionService,
                                            FileStorageService fileStorageService) {
        this.faceDetectionService = faceDetectionService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * POST /api/face/detect-uploaded?fileName=....
     *
     * fileName е това, което ти връща /api/images/upload (например 16877917_test.jpg).
     * Endpoint-ът ще:
     *  1) конструира пълния път до файла (в uploads/)
     *  2) ще извика Python модела с този път
     *  3) ще върне JSON с hasFace, faceRectangle, fingerprint
     */
    @PostMapping("/detect-uploaded")
    public ResponseEntity<?> detectOnUploaded(@RequestParam String fileName) {
        try {
            // 1) Пълен път до файла в uploads/
            Path filePath = fileStorageService.getFilePath(fileName);

            // 2) Викаме Python модела с този път
            JsonNode result = faceDetectionService.detectOnPath(filePath.toString());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error detecting face on uploaded image: " + e.getMessage());
        }
    }
}
