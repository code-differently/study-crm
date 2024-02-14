package com.codedifferently.studycrm.entities.api.web;

import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PropertyWidgetResponse extends WidgetResponse {

  private UUID propertyId;
}
