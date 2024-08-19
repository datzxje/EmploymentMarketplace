package com.empmarket.employmentmarketplace.dto.req;

import com.empmarket.employmentmarketplace.entity.Job;
import com.empmarket.employmentmarketplace.entity.User;
import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;
import lombok.Data;

import java.time.Instant;

@Data
public class ResumeDto {

    private String email;

    private String url;

    private ResumeStateEnum state;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    private User user;

    private Job job;

}
