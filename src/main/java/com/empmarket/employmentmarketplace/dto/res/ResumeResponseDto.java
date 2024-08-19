package com.empmarket.employmentmarketplace.dto.res;

import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
public class ResumeResponseDto {

    private Long id;

    private String email;

    private String url;

    @Enumerated(EnumType.STRING)
    private ResumeStateEnum state;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    private UserResume user;

    private JobResume job;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResume {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobResume {
        private Long id;
        private String name;
    }

}
