package com.fer.hr.product.service.impl;

import com.fer.hr.product.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String saveImageToStorage(MultipartFile image) throws IOException {
        String uploadDirectory = "image-uploads";

        String uniqueFileName = UUID.randomUUID().toString() + getFileExtension(image.getOriginalFilename());

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = Path.of(uploadDirectory + "/" + uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;

    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex);
    }
}
