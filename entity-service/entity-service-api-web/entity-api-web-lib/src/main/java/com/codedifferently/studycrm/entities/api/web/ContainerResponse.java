package com.codedifferently.studycrm.entities.api.web;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContainerResponse {

  private String templateRegion;

  private String type;

  private List<WidgetResponse> widgets;
}
