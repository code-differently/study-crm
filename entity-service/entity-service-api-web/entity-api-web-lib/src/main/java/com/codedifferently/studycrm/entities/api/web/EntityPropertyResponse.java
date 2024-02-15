package com.codedifferently.studycrm.entities.api.web;

import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class EntityPropertyResponse {

  private String name;

  private String typeName;

  private Object value;

  private Date updatedAt;
}
