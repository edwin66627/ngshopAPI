package com.ngshop.utils;

import com.ngshop.constant.ExceptionMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStorage {

    private String imagesPath;

    public FileStorage() throws IOException {
        imagesPath = new File("src/main/resources/static/image/").getAbsolutePath();
    }

    public String uploadFile(MultipartFile[] images) throws IOException, IllegalArgumentException {
        if(!isSupportedContentType(images)){
            throw new IllegalArgumentException(ExceptionMessage.UNSUPPORTED_IMAGE_FILE);
        }

        String imagesString = "";
        for(MultipartFile file: images){
            String filename = UUID.randomUUID().toString() + "_" +file.getOriginalFilename();
            String filePath = this.imagesPath + "/" + filename;
            file.transferTo(new File(filePath));
            imagesString += filename +",";
        }

        imagesString = imagesString.substring(0, imagesString.length() -1);
        return imagesString;
    }

    private boolean isSupportedContentType(MultipartFile[] images) {
        boolean isSupported = false;
        for(MultipartFile file: images){
            String contentType = file.getContentType();
            if(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg")){
               isSupported = true;
            }else{
                isSupported = false;
                return isSupported;
            }
        }
        return isSupported;
    }

}
