package com.ngshop.utils;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.exception.domain.FileSizeNotAllowedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorage {

    @Value("${maximum.file.size}")
    long maxFileSize;
    private String imagesPath;

    public FileStorage() throws IOException {
        imagesPath = new File("src/main/resources/static/image/").getAbsolutePath();
    }

    public String uploadFile(MultipartFile[] images) throws IOException, IllegalArgumentException {

        if(!isSupportedContentType(images)){
            throw new IllegalArgumentException(ExceptionMessage.UNSUPPORTED_IMAGE_FILE);
        }
        if(!isIndividualFileSizeAllowed(images)){
            throw new FileSizeNotAllowedException(ExceptionMessage.FILE_SIZE_NOT_ALLOWED);
        }

        String imagesString = "";

        for(int i = 0; i < images.length; i++){
            String originalFilename = images[i].getOriginalFilename().replaceAll(" ", "_").toLowerCase();
            String filename = UUID.randomUUID().toString().substring(0, 5) + "_" +originalFilename;
            String filePath = this.imagesPath + "/" + filename;
            images[i].transferTo(new File(filePath));
            imagesString += filename;
            if(i != images.length - 1)
                imagesString += ",";
        }

        return imagesString;
    }

    public void deleteFiles(List<String> files) throws IOException {
        for(String filename: files){
            Path fileToDeletePath = Paths.get(imagesPath+"/"+filename);
            Files.delete(fileToDeletePath);
        }
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

    private boolean isIndividualFileSizeAllowed(MultipartFile[] images) throws IOException {
        boolean isFileSizeAllowed = false;
        for(MultipartFile file: images){
            long fileSize = file.getSize() / (1024);
            if(fileSize > maxFileSize){
                return isFileSizeAllowed;
            }
        }
        return !isFileSizeAllowed;
    }

}
