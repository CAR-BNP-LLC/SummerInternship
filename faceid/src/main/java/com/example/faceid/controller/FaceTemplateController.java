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

    /**
     * POST /api/templates/from-saved-image?personName=Ivan
     *
     * Взима fingerprint от локалната снимка (images/person1.jpg – според FaceDetectionService)
     * и го записва в базата като FaceTemplate.
     */
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
}
