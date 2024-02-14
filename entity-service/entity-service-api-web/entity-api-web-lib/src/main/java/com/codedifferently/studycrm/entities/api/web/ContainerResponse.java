package com.codedifferently.studycrm.entities.api.web;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContainerResponse {

    private List<WidgetResponse> widgets;
}
