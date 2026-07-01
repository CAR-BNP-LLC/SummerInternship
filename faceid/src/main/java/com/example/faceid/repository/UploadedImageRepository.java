package com.example.faceid.repository;

import com.example.faceid.model.UploadedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {

    // всички снимки, подредени по дата на качване (последните първи)
    List<UploadedImage> findAllByOrderByUploadedAtDesc();
}
