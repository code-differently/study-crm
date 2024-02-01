package com.codedifferently.studycrm.common.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class EntityBase {

    @Id
    @GeneratedValue
    @JdbcType(VarcharJdbcType.class)
    protected UUID id;

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
