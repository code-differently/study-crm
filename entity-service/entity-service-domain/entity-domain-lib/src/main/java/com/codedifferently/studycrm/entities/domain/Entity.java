package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@jakarta.persistence.Entity
@Table(name = "Entity")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Entity extends EntityBase {

  @ManyToOne(fetch = FetchType.EAGER)
  private EntityType entityType;

  @Column(nullable = false)
  private UUID organizationId;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "entity")
  private List<EntityProperty> properties;
}
