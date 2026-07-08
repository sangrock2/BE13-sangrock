package org.example.spring_mission_join.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_mission_join.exception.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {
    @Value("${spring.file.upload-dir}")
    private String uploadDir;

    public Resource downloadFile(String fileName) {
        try {
            File targetFile = new File(uploadDir).getAbsoluteFile();
            File file = new File(targetFile, fileName);

            Resource resource = new UrlResource(file.toURI());

            if (!resource.exists() || !resource.isReadable()) {
                throw new BoardNotFoundException("[BOARD] Board not found : " + fileName);
            }

            return resource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            File targetFile = new File(uploadDir).getAbsoluteFile();

            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File destFile = new File(targetFile, fileName);

            file.transferTo(destFile);
            return destFile.getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return;
        }

        File file = new File(filePath);

        if (!file.exists()) {
            return;
        }

        file.delete();
    }
}
