package com.codedifferently.studycrm.entities.layout.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "widget_type")
@SuperBuilder
public class Widget extends EntityBase {

  @ManyToOne private Group parentWidget;

  private String label;

  private boolean hideLabel;

  private int displayOrder;
}
