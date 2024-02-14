package com.codedifferently.studycrm.entities.layout.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "widget_type")
@SuperBuilder
public class Widget extends EntityBase {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_widget_id")
  private GroupWidget parentWidget;

  protected String label;

  protected boolean hideLabel;

  protected int displayOrder;
}
