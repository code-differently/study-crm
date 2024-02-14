package com.codedifferently.studycrm.entities.api.web;

import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GroupWidgetResponse extends WidgetResponse {

  private UUID propertyGroupId;

  private List<WidgetResponse> widgets;
}
