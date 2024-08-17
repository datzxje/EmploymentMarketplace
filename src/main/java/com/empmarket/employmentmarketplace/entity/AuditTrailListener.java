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
    }

    @PreUpdate
    private void beforeAnyUpdate(Object entity) {
        if(entity instanceof Company company) {
            company.setUpdatedAt(Instant.now());
            company.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ?
                    SecurityUtil.getCurrentUserLogin().get() : "");
            log.info("Company updated by: " + company.getUpdatedBy());
        }
    }
}
