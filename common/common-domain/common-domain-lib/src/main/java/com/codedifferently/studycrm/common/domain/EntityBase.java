package com.codedifferently.studycrm.common.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class EntityBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;

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
