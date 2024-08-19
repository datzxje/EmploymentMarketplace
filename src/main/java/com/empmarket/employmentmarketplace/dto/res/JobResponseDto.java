package com.empmarket.employmentmarketplace.dto.res;

import com.empmarket.employmentmarketplace.enums.LevelEnum;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class JobResponseDto {

    private Long id;

    private String name;

    private String location;

    private Double salary;

    private Integer quantity;

    private LevelEnum level;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    private List<String> skills;

}
