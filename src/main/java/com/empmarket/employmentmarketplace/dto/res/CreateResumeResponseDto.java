package com.empmarket.employmentmarketplace.dto.res;

import lombok.Data;

import java.time.Instant;

@Data
public class CreateResumeResponseDto {

    private Long id;

    private Instant createdAt;

    private String createdBy;

}
