package com.empmarket.employmentmarketplace.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UploadFileResponseDto {

    private String fileName;

    private Instant uploadedAt;

}
