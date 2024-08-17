package com.empmarket.employmentmarketplace.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CompanyDto {

    private String name;

    private String description;

    private String address;

    private String logo;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;
}
