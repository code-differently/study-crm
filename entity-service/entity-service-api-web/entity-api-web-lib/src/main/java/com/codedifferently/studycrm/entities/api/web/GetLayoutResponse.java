package com.codedifferently.studycrm.entities.api.web;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLayoutResponse {

  private UUID id;

  private UUID organizationId;

  private String entityType;

  private String templateName;

  @Singular private List<ContainerResponse> containers;
}
