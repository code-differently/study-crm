package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@jakarta.persistence.Entity
@Table(name = "Property")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Property extends EntityBase {

  @ManyToOne(fetch = FetchType.LAZY)
  private PropertyType propertyType;

  @Column(nullable = false)
  private String name;

  private String label;

  private String pluralLabel;

  private String description;

  private String format;

  private UUID ownerId;

  private boolean isRequired;

  private boolean isUnique;
}
