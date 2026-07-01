package com.example.faceid.repository;

import com.example.faceid.model.FaceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceTemplateRepository extends JpaRepository<FaceTemplate, Long> {
    // засега нямаме специални методи – по-късно може да добавим findByPersonName и т.н.
}
