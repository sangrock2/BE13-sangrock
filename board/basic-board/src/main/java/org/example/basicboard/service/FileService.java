package org.example.basicboard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${spring.file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        if (file == null | file.isEmpty()) {
            return null;
        }

        try {
            File dir = new File(uploadDir).getAbsoluteFile();

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String storeFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, storeFileName);

            file.transferTo(dest);

            return dest.getPath();
        } catch (Exception e) {
            log.error("File store failed: originalFileName={}", file.getOriginalFilename(), e);
            throw new IllegalStateException("failed to store file", e);
        }
    }

    public Resource downloadFile(String fileName) {
        try {
            File file = new File(new File(uploadDir).getAbsoluteFile(), fileName);

            // 파일을 가리키는 Resource 생성(실제로 읽어 들이는게 아니라 위치만 잡음)
            Resource resource = new UrlResource(file.toURI());

            if (!resource.exists() || !resource.isReadable()) {
                log.warn("File download failed: fileName={}", fileName);
                throw new IOException("Could not read file: " + fileName);
            }

            return resource;
        } catch (MalformedInputException e) {
            log.error("File download failed: fileName={}", fileName, e);
            throw new IllegalStateException("failed to download file: " + fileName, e);
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

        if (!file.delete()) {
            log.warn("File delete failed: filePath={}", filePath);
        }
    }
}
