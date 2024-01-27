package com.codedifferently.studycrm.common.domain;

import lombok.Data;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

import java.util.Date;

@MappedSuperclass
public abstract class EntityBase {

    @Version
    protected Integer version;

    protected String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    protected String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = this.updatedAt = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = new Date();
    }
}
