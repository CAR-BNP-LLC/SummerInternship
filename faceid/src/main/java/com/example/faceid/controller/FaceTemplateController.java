package com.example.faceid.controller;

import com.example.faceid.model.FaceTemplate;
import com.example.faceid.service.FaceTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
@CrossOrigin(origins = "*")
public class FaceTemplateController {

    private final FaceTemplateService faceTemplateService;

    public FaceTemplateController(FaceTemplateService faceTemplateService) {
        this.faceTemplateService = faceTemplateService;
    }

    @PostMapping("/from-saved-image")
    public ResponseEntity<?> createFromSavedImage(@RequestParam(required = false) String personName) {
        try {
            FaceTemplate template = faceTemplateService.createTemplateFromSavedImage(personName);
            return ResponseEntity.ok(template);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Грешка при създаване на template: " + e.getMessage());
        }
    }

    @GetMapping("/from-saved-image")
    public ResponseEntity<?> createFromSavedImageGet(@RequestParam(required = false) String personName) {
        try {
            FaceTemplate template = faceTemplateService.createTemplateFromSavedImage(personName);
            return ResponseEntity.ok(template);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Грешка при създаване на template: " + e.getMessage());
        }
    }
}
