package com.empmarket.employmentmarketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@EntityListeners(AuditTrailListener.class)
@Entity
@Data
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;

}
