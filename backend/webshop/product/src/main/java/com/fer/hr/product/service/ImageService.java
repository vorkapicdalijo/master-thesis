package com.fer.hr.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    public String saveImageToStorage(MultipartFile image) throws IOException;
}
