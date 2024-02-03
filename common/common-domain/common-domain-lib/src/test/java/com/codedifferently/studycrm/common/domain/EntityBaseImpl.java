package com.codedifferently.studycrm.common.domain;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode
@SuperBuilder
@Generated
public class EntityBaseImpl extends EntityBase {
  public EntityBaseImpl() {
    super();
  }

  public EntityBaseImpl(
      java.util.UUID id,
      int version,
      java.util.Date createdAt,
      String createdBy,
      java.util.Date updatedAt,
      String updatedBy) {
    super(id, version, createdBy, createdAt, updatedBy, updatedAt);
  }
}
