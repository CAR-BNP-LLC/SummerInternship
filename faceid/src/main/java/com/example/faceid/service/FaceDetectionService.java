package com.example.faceid.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FaceDetectionService {

    @Value("${face.model.url}")
    private String modelUrl; // напр. http://localhost:5000/detect-local-image

    @Value("${face.image.path}")
    private String defaultImagePath; // напр. images/person1.jpg (по избор за тест)

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FaceDetectionService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Вика Python модела с imagePath, подаден като параметър.
     * Изпраща JSON: {"imagePath": "..."} и връща JSON отговора.
     */
    public JsonNode detectOnPath(String imagePath) throws Exception {
        // 1) Строим JSON body: {"imagePath": "<imagePath>"}
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("imagePath", imagePath);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        // 2) HTTP заглавки
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // 3) Изпращаме POST към Python модела
        ResponseEntity<String> response = restTemplate.exchange(
                modelUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        // 4) Проверка за статус
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(
                    "Face model error: " + response.getStatusCode() + " - " + response.getBody()
            );
        }

        // 5) Връщаме JSON отговора от Python-а
        return objectMapper.readTree(response.getBody());
    }

    /**
     * За удобство – стар метод, който ползва defaultImagePath от application.properties.
     */
    public JsonNode detectFacesRaw() throws Exception {
        return detectOnPath(defaultImagePath);
    }

    public JsonNode getFirstFaceFingerprint() throws Exception {
        return detectFacesRaw();
    }
}
