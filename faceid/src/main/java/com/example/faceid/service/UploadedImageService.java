package com.example.faceid.service;

import com.example.faceid.model.UploadedImage;
import com.example.faceid.repository.UploadedImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadedImageService {

    private final UploadedImageRepository uploadedImageRepository;

    public UploadedImageService(UploadedImageRepository uploadedImageRepository) {
        this.uploadedImageRepository = uploadedImageRepository;
    }

    public UploadedImage saveUploadedImage(String storedFileName, String originalFileName) {
        UploadedImage img = new UploadedImage(storedFileName, originalFileName);
        return uploadedImageRepository.save(img);
    }

    public List<UploadedImage> getAllImagesOrdered() {
        return uploadedImageRepository.findAllByOrderByUploadedAtDesc();
    }
}
