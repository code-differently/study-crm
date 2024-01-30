package com.codedifferently.studycrm.common.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class EntityBase {

    @Version
    @Builder.Default
    protected Integer version = 0;

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
