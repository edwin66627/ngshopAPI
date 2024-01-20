package com.ngshop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class FileStorage {

    @Value("${base.url}")
    private String baseUrl;
    private String imagesPath;

    public FileStorage() throws IOException {
        imagesPath = new File("src/main/resources/static/image/").getAbsolutePath();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String filePath = this.imagesPath + "/" +file.getOriginalFilename();
        file.transferTo(new File(filePath));
        String imageUrl = baseUrl + file.getOriginalFilename();
        return imageUrl;
    }

}
