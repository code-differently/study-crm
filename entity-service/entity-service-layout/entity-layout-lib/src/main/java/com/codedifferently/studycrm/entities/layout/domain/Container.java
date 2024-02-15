package com.codedifferently.studycrm.entities.layout.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Container extends EntityBase {

  @ManyToOne private Layout layout;

  private String label;

  private String templateRegion;

  private String containerType;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Widget> widgets;
}
