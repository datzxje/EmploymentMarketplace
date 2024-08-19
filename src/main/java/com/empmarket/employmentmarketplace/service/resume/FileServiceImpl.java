package com.empmarket.employmentmarketplace.service.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${upload-folder.base-uri}")
    private String baseURI;

    public void createDirectory(String folder) throws URISyntaxException, IOException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);

        // Check if the directory already exists
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                System.out.println("Directory already exists: " + folder);
            } else {
                throw new FileAlreadyExistsException("Path exists but is not a directory: " + folder);
            }
        } else {
            try {
                Files.createDirectories(path);
                System.out.println("Directory created: " + folder);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {

        String finalName = generateUniqueFileName(file.getOriginalFilename()) ;

        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }

        return finalName;

    }

    private static String generateUniqueFileName(String fileName) {
        // Extract the file extension
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = fileName.substring(dotIndex);
            fileName = fileName.substring(0, dotIndex);
        }

        // Create a UUID object
        UUID uuid = UUID.randomUUID();

        // Return the formatted unique file name
        return fileName + uuid.toString() + extension;
    }
}
