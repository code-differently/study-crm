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
@DiscriminatorValue("not null")
@SuperBuilder
public class Widget extends EntityBase {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_widget_id")
  private GroupWidget parentWidget;

  @Column(name = "widget_type", insertable = false, updatable = false)
  @Getter
  private String type;

  protected String label;

  protected boolean hideLabel;

  protected int displayOrder;
}
