package com.codedifferently.studycrm.entities.api.web;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class GetEntitiesResponse {

  private List<GetEntityResponse> entities;
}
