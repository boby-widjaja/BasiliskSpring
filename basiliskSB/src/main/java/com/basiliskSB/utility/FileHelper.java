package com.basiliskSB.utility;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;

public class FileHelper {
    public static String uploadProductPhoto(String fileName, MultipartFile multipartFile) throws IOException {
        var uploadDirectory = "src/main/resources/static/resources/image/product/";
        var uploadPath = Paths.get(uploadDirectory);
        if(fileName == null || fileName.equals("")){
            var uuid = UUID.randomUUID();
            fileName = String.format("%s.jpg", uuid.toString());
        }
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
        }
        return fileName;
    }

    public static void deleteProductPhoto(String fileName){
        var uploadDirectory = "src/main/resources/static/resources/image/product/";
        var uploadPath = Paths.get(uploadDirectory);
        var filePath = uploadPath.resolve(fileName);
        File file = new File(filePath.toString());
        file.delete();
    }
}
