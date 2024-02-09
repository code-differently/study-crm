package com.codedifferently.studycrm.entities.api.web;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class GetEntityResponse {

  private UUID id;

  private String type;

  private List<EntityPropertyResponse> properties;

  private Date createdAt;

  private Date updatedAt;
}
