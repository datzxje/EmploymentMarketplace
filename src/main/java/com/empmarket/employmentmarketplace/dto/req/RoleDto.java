package com.empmarket.employmentmarketplace.dto.req;

import com.empmarket.employmentmarketplace.entity.Permission;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class RoleDto {

    @NotBlank(message = "require name")
    private String name;

    private String description;

    private boolean active;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

    private List<Permission> permissions;
}
