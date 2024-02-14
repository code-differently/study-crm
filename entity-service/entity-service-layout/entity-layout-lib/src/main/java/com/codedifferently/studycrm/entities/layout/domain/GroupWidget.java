package com.codedifferently.studycrm.entities.layout.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Table(name = "[group]")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("group")
public class GroupWidget extends Widget {

  private UUID propertyGroupId;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parentWidget")
  private List<Widget> widgets;
}
