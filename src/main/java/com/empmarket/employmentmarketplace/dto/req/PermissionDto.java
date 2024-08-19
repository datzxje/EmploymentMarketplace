package com.empmarket.employmentmarketplace.dto.req;

import com.empmarket.employmentmarketplace.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class PermissionDto {

    @NotBlank(message = "require name")
    private String name;

    @NotBlank(message = "require name")
    private String apiPath;

    @NotBlank(message = "require name")
    private String method;

    @NotBlank(message = "require name")
    private String module;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    private List<Role> roles;

}
