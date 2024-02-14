package com.codedifferently.studycrm.entities.api.web;

import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FieldWidgetResponse extends WidgetResponse {
    
  private UUID propertyId;
}
