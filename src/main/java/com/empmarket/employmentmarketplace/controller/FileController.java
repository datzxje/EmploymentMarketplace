package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.res.UploadFileResponseDto;
import com.empmarket.employmentmarketplace.service.resume.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController {

    @Value("${upload-folder.base-uri}")
    private String baseURI;

    private final FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<?> uploadFile(@RequestParam(name = "file", required = false) MultipartFile file,
                                        @RequestParam("folder") String folder) throws URISyntaxException, IOException {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtentions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtentions.stream().anyMatch(fileExtention -> fileName.toLowerCase().endsWith(fileExtention));

        if (!isValid) {
            throw new FileUploadException("Invalid file format. Allowed extensions are: " + allowedExtentions);
        }
        fileService.createDirectory(baseURI + folder);
        String uploadFile = fileService.store(file, folder);

        UploadFileResponseDto res = new UploadFileResponseDto(uploadFile, Instant.now());

        return ResponseEntity.ok().body(res);
    }

}
