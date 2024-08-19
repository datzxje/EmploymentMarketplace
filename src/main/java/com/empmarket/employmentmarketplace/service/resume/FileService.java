package com.empmarket.employmentmarketplace.service.resume;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FileService {

    void createDirectory(String folder) throws URISyntaxException, IOException;

    String store(MultipartFile file, String folder) throws URISyntaxException, IOException;

}
