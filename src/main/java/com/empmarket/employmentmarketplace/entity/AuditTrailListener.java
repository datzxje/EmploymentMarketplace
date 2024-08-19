package com.empmarket.employmentmarketplace.entity;

import com.empmarket.employmentmarketplace.util.SecurityUtil;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.Instant;

public class AuditTrailListener {

    private static Log log = LogFactory.getLog(AuditTrailListener.class);

    @PrePersist
    private void beforeAnyPersist(Object entity) {
        if(entity instanceof Company company) {
            company.setCreatedAt(Instant.now());
            company.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
            log.info("Company created by: " + company.getCreatedBy());
        }
        else if (entity instanceof User user) {
            user.setCreatedAt(Instant.now());
        }
        else if (entity instanceof Job job) {
            job.setCreatedAt(Instant.now());
            job.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
        }
        else if (entity instanceof Skill skill) {
            skill.setCreatedAt(Instant.now());
            skill.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
        } else if (entity instanceof Resume resume) {
            resume.setCreatedAt(Instant.now());
            resume.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
        }
    }

    @PreUpdate
    private void beforeAnyUpdate(Object entity) {
        if(entity instanceof Company company) {
            company.setUpdatedAt(Instant.now());
            company.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
            log.info("Company updated by: " + company.getUpdatedBy());
        }
        else if (entity instanceof User user) {
            user.setUpdatedAt(Instant.now());
        }
        else if (entity instanceof Job job) {
            job.setUpdatedAt(Instant.now());
            job.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
        }
        else if (entity instanceof Skill skill) {
            skill.setUpdatedAt(Instant.now());
            skill.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
        }
        else if (entity instanceof Resume resume) {
            resume.setUpdatedAt(Instant.now());
            resume.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
        }
    }
}
