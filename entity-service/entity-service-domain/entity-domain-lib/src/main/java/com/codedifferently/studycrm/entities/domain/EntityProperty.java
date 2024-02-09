package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@jakarta.persistence.Entity
@Table(name = "EntityProperty")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EntityProperty extends EntityBase {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Entity entity;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Property property;

  private String value;
}
