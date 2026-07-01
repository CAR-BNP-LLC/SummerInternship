package com.example.faceid.controller;

import com.example.faceid.model.UploadedImage;
import com.example.faceid.service.FileStorageService;
import com.example.faceid.service.UploadedImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageUploadController {

    private final FileStorageService fileStorageService;
    private final UploadedImageService uploadedImageService;

    public ImageUploadController(FileStorageService fileStorageService,
                                 UploadedImageService uploadedImageService) {
        this.fileStorageService = fileStorageService;
        this.uploadedImageService = uploadedImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Received file: " + file.getOriginalFilename() + ", size: " + file.getSize());

            String storedFileName = fileStorageService.storeFile(file);
            String originalName = file.getOriginalFilename();

            UploadedImage saved = uploadedImageService.saveUploadedImage(storedFileName, originalName);

            return ResponseEntity.ok(Map.of(
                    "id", saved.getId(),
                    "fileName", saved.getFileName(),
                    "originalName", saved.getOriginalName(),
                    "uploadedAt", saved.getUploadedAt(),
                    "message", "File uploaded successfully"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<UploadedImage>> getRecentImages() {
        List<UploadedImage> images = uploadedImageService.getAllImagesOrdered();
        return ResponseEntity.ok(images);
    }
}
