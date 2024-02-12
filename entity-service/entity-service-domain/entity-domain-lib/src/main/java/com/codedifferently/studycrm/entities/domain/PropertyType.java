package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyType extends EntityBase {

  private String name;

  private String label;

  private String semanticType;

  private String wireType;

  private boolean isNumeric;

  private boolean isTimestamp;
}
