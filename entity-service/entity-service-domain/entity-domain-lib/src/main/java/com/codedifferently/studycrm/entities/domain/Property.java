package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Property extends EntityBase {

  @ManyToOne(fetch = FetchType.EAGER)
  private PropertyType propertyType;

  private UUID propertyGroupId;

  @Column(nullable = false)
  private String name;

  private String label;

  private String pluralLabel;

  private String description;

  private String format;

  private UUID organizationId;

  private boolean isRequired;

  private boolean isUnique;
}
