package com.empmarket.employmentmarketplace.dto.req;

import com.empmarket.employmentmarketplace.entity.Skill;
import com.empmarket.employmentmarketplace.enums.LevelEnum;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class JobDto {

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

    private List<Skill> skills;

}
