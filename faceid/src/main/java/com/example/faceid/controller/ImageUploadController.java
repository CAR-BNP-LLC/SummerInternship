package com.example.faceid.controller;

import com.example.faceid.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageUploadController {

    private final FileStorageService fileStorageService;

    public ImageUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * POST /api/images/upload
     * Form-data: file = <image>
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            // Можем да върнем и пътя, който после ще подадем на Python модела
            return ResponseEntity.ok(Map.of(
                    "fileName", fileName,
                    "message", "File uploaded successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }
}
