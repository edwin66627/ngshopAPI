package com.ngshop.utils;

import com.ngshop.constant.ExceptionMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorage {

    @Value("${base.url}")
    private String baseUrl;
    private String imagesPath;

    public FileStorage() throws IOException {
        imagesPath = new File("src/main/resources/static/image/").getAbsolutePath();
    }

    public String uploadFile(MultipartFile file) throws IOException, IllegalArgumentException {
        if(!isSupportedContentType(file.getContentType())){
            throw new IllegalArgumentException(ExceptionMessage.UNSUPPORTED_IMAGE_FILE);
        }
        String filePath = this.imagesPath + "/" +file.getOriginalFilename();
        file.transferTo(new File(filePath));
        String imageUrl = baseUrl + file.getOriginalFilename();
        return imageUrl;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg");
    }

}
