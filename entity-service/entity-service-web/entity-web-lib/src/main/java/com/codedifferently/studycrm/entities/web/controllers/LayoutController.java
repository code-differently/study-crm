package com.codedifferently.studycrm.entities.web.controllers;

import com.codedifferently.studycrm.entities.api.web.*;
import com.codedifferently.studycrm.entities.layout.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations/{organizationId}/layouts")
public class LayoutController {

  @Autowired private LayoutService layoutService;

  @GetMapping
  public ResponseEntity<GetLayoutsResponse> getAll(@RequestParam("entityType") String entityType) {
    List<Layout> layouts = layoutService.findAllByEntityType(entityType);
    List<GetLayoutResponse> layoutResponses =
        layouts.stream().map(LayoutController::getLayoutResponse).collect(Collectors.toList());

    return ResponseEntity.ok(GetLayoutsResponse.builder().layouts(layoutResponses).build());
  }

  private static GetLayoutResponse getLayoutResponse(Layout layout) {
    return GetLayoutResponse.builder()
        .id(layout.getId())
        .organizationId(layout.getOrganizationId())
        .entityType(layout.getEntityType())
        .containers(getContainerResponses(layout.getContainers()))
        .build();
  }

  private static List<ContainerResponse> getContainerResponses(List<Container> containers) {
    return containers.stream()
        .map(LayoutController::getContainerResponse)
        .collect(Collectors.toList());
  }

  private static ContainerResponse getContainerResponse(Container container) {
    return ContainerResponse.builder().widgets(getWidgetResponses(container.getWidgets())).build();
  }

  private static List<WidgetResponse> getWidgetResponses(List<Widget> widgets) {
    var responses = new ArrayList<WidgetResponse>();

    for (Widget widget : widgets) {
      var builder = WidgetResponse.builder();

      if (widget instanceof GroupWidget) {
        builder = getGroupWidgetBuilder((GroupWidget) widget);
      } else if (widget instanceof PropertyWidget) {
        builder = getPropertyWidgetBuilder((PropertyWidget) widget);
      }

      responses.add(
          builder
              .type(widget.getType())
              .label(widget.getLabel())
              .hideLabel(widget.isHideLabel())
              .displayOrder(widget.getDisplayOrder())
              .build());
    }

    return responses;
  }

  private static WidgetResponse.WidgetResponseBuilder<?, ?> getGroupWidgetBuilder(
      GroupWidget groupWidget) {
    return GroupWidgetResponse.builder()
        .propertyGroupId(groupWidget.getPropertyGroupId())
        .widgets(getWidgetResponses(groupWidget.getWidgets()));
  }

  private static WidgetResponse.WidgetResponseBuilder<?, ?> getPropertyWidgetBuilder(
      PropertyWidget propertyWidget) {
    return PropertyWidgetResponse.builder().propertyId(propertyWidget.getPropertyId());
  }
}
