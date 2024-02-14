package com.codedifferently.studycrm.entities.api.web;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WidgetResponse {

    protected String label;
  
    protected boolean hideLabel;
  
    protected int displayOrder;
}
