package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
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
@Generated
public class Entity extends EntityBase {

  private String firstName;

  private String lastName;
}
