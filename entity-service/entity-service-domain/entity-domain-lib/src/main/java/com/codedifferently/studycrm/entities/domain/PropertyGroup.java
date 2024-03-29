package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.util.List;
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
public class PropertyGroup extends EntityBase {

  private UUID entityTypeId;

  private String label;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyGroupId", cascade = CascadeType.ALL)
  private List<Property> properties;
}
