package com.example.faceid.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService(@Value("${upload.dir}") String uploadDir) throws IOException {
        // Превръщаме в абсолютен път и нормализираме (махаме .., . и т.н.)
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        // Създаваме папката, ако не съществува
        Files.createDirectories(this.uploadDir);
    }

    /**
     * Записва качен файл в uploadDir и връща името на запазения файл.
     * Пример: качваш "myphoto.jpg" → връща нещо като "1720000000000_myphoto.jpg".
     */
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Empty file");
        }

        // Вземаме само името на файла (без път, за безопасност)
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("File has no name");
        }
        String cleanFilename = Paths.get(originalFilename).getFileName().toString();

        // За уникалност добавяме currentTimeMillis преди името
        String storedFileName = System.currentTimeMillis() + "_" + cleanFilename;

        Path targetLocation = uploadDir.resolve(storedFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return storedFileName;
    }

    /**
     * Връща абсолютния път до файл по име (примерно това, което storeFile() е върнало).
     */
    public Path getFilePath(String fileName) {
        return uploadDir.resolve(fileName).toAbsolutePath().normalize();
    }
}
