package com.empmarket.employmentmarketplace.dto.req;

import lombok.Data;

import java.time.Instant;

@Data
public class SkillDto {

    private String name;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

}
