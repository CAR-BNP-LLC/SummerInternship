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
    private String modelUrl; // напр. http://localhost:5000/detect-local-image

    @Value("${face.image.path}")
    private String defaultImagePath; // напр. images/person1.jpg

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FaceDetectionService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    // За да видим реалната стойност на modelUrl при старт
    @PostConstruct
    public void init() {
        System.out.println("modelUrl from properties: [" + modelUrl + "]");
        System.out.println("defaultImagePath from properties: [" + defaultImagePath + "]");
    }

    /**
     * Вика локалния Python модел (face_server.py) с произволен път до изображение.
     * Изпраща JSON: {"imagePath": "<imagePath>"}
     * Връща JSON отговора от Python-а като JsonNode.
     */
    public JsonNode detectOnPath(String imagePath) {
        try {
            // 1) Подготвяме JSON body
            JsonNode requestBody = objectMapper.createObjectNode()
                    .put("imagePath", imagePath);

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            System.out.println("Sending to model (" + modelUrl + "): " + jsonBody);

            // 2) Заглавки
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            // 3) Заявка към Python
            ResponseEntity<String> response = restTemplate.exchange(
                    modelUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            System.out.println("Model response status: " + response.getStatusCode());
            System.out.println("Model response body: " + response.getBody());

            // 4) Проверка на статуса
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException(
                        "Face model error: " + response.getStatusCode() + " - " + response.getBody()
                );
            }

            // 5) Парсваме JSON
            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling face model", e);
        }
    }

    /**
     * Вика модела с defaultImagePath (images/person1.jpg).
     */
    public JsonNode getFirstFaceFingerprint() {
        return detectOnPath(defaultImagePath);
    }
}
