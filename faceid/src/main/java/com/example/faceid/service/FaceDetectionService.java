package com.example.faceid.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FaceDetectionService {

    @Value("${face.image.path}")
    private String imagePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getFirstFaceFingerprint() throws IOException {
        // Проверка, че снимката съществува
        ClassPathResource imgFile = new ClassPathResource(imagePath);
        if (!imgFile.exists()) {
            throw new IOException("Image not found at path: " + imagePath);
        }

        // Временен фиктивен резултат
        String fakeJson = """
            {
              "hasFace": true,
              "faceId": "dummy-id-123",
              "faceRectangle": {
                "top": 100,
                "left": 80,
                "width": 150,
                "height": 150
              }
            }
            """;

        return objectMapper.readTree(fakeJson);
    }
}
