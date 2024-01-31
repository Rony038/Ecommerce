package com.project.ecommerce.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.ecommerce.exception.StorageException;

import io.jsonwebtoken.io.IOException;

@Service
public class ImageService {
	@Value("${upload.path}")
    private String uploadPath; // Path to store uploaded images
    
    public String store(MultipartFile file) throws java.io.IOException {
        try {
            byte[] bytes = file.getBytes();
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);
            return fileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }
    
    public byte[] load(String imageUrl) throws java.io.IOException {
        try {
            Path path = Paths.get(uploadPath + imageUrl);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new StorageException("Failed to load image", e);
        }
    }

}
