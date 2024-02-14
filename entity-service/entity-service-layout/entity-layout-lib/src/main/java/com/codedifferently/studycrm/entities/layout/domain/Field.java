package com.codedifferently.studycrm.entities.layout.domain;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("field")
public class Field extends Widget {

  private UUID propertyId;
}
