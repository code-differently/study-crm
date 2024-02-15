package com.codedifferently.studycrm.entities.api.web;

import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyResponse {

  private UUID id;

  private PropertyTypeResponse type;

  private UUID groupId;

  private String name;

  private String label;

  private String pluralLabel;

  private String description;

  private String format;

  private boolean isRequired;

  private boolean isUnique;
}
