package com.amaze.utility;

import com.amaze.exception.FileInvalidExtensionException;
import com.amaze.exception.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class FileUtility {

    private FileUtility() {
        //for sonarqube
    }

    public static void validateFile(MultipartFile file){
        if(file.isEmpty())
            throw new FileNotFoundException("Please select file to upload");

        List<String> allowedExtensions = List.of("png", "jpeg", "jpg", "pdf", "docx", "pages");

        String filename = file.getOriginalFilename();
        assert filename != null;
        String extension = filename.split("\\.")[1];

        if(!allowedExtensions.contains(extension))
            throw new FileInvalidExtensionException(extension + " not allowed");

    }
}