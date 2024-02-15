package com.codedifferently.studycrm.entities.api.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyTypeResponse {

  private String name;

  private String label;

  private String semanticType;

  private String wireType;

  private boolean isNumeric;

  private boolean isTimestamp;
}
