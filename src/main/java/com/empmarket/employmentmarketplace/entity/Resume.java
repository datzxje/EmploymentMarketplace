package com.empmarket.employmentmarketplace.entity;

import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@EntityListeners(AuditTrailListener.class)
@Entity
@Data
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Require email")
    private String email;

    @NotBlank(message = "Reupload CV")
    private String url;

    @Enumerated(EnumType.STRING)
    private ResumeStateEnum state;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

}
