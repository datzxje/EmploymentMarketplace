package com.empmarket.employmentmarketplace.dto.res;

import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateResumeResponseDto {

    private ResumeStateEnum state;

    private Instant updatedAt;

    private String updatedBy;

}
