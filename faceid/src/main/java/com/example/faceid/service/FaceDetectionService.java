package com.example.faceid.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FaceDetectionService {

    @Value("${face.model.url}")
    private String modelUrl; // http://localhost:5000/detect-local-image

    @Value("${face.image.path}")
    private String defaultImagePath; // images/person1.jpg

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FaceDetectionService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void init() {
        System.out.println("modelUrl from properties: [" + modelUrl + "]");
        System.out.println("defaultImagePath from properties: [" + defaultImagePath + "]");
    }

    public JsonNode detectOnPath(String imagePath) {
        try {
            JsonNode requestBody = objectMapper.createObjectNode()
                    .put("imagePath", imagePath);

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            System.out.println("Sending to model (" + modelUrl + "): " + jsonBody);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    modelUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            System.out.println("Model response status: " + response.getStatusCode());
            System.out.println("Model response body: " + response.getBody());

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException(
                        "Face model error: " + response.getStatusCode() + " - " + response.getBody()
                );
            }

            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling face model", e);
        }
    }

    public JsonNode getFirstFaceFingerprint() {
        return detectOnPath(defaultImagePath);
    }
}
