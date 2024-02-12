package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EntityType extends EntityBase {

  @Column(unique = true, nullable = false)
  private String name;

  private String label;

  private String pluralLabel;

  private String entityClass;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "entityTypeId")
  private List<PropertyGroup> propertyGroups;
}
