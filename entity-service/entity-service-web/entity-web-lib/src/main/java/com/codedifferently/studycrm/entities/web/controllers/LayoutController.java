package com.codedifferently.studycrm.entities.web.controllers;

import com.codedifferently.studycrm.entities.api.web.*;
import com.codedifferently.studycrm.entities.domain.Property;
import com.codedifferently.studycrm.entities.domain.PropertyService;
import com.codedifferently.studycrm.entities.domain.PropertyType;
import com.codedifferently.studycrm.entities.layout.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations/{organizationId}/layouts")
public class LayoutController {

  @Autowired private LayoutService layoutService;

  @Autowired private PropertyService propertyService;

  @GetMapping
  public ResponseEntity<GetLayoutsResponse> getAll(@RequestParam("entityType") String entityType) {
    List<Layout> layouts = layoutService.findAllByEntityType(entityType);

    List<GetLayoutResponse> layoutResponses =
        layouts.stream().map(LayoutController::getLayoutResponse).collect(Collectors.toList());
    List<PropertyResponse> propertyResponses = getPropertiesFromWidgets(layouts);

    return ResponseEntity.ok(
        GetLayoutsResponse.builder()
            .layouts(layoutResponses)
            .properties(propertyResponses)
            .build());
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

    if (widgets == null) {
      return responses;
    }

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

  private List<PropertyResponse> getPropertiesFromWidgets(List<Layout> layouts) {
    List<UUID> propertyIds = getPropertyIds(layouts);
    Iterable<Property> properties = propertyService.getProperties(propertyIds);
    List<PropertyResponse> propertyResponses =
        StreamSupport.stream(properties.spliterator(), false)
            .map(LayoutController::getPropertyResponse)
            .collect(Collectors.toList());
    return propertyResponses;
  }

  private static List<UUID> getPropertyIds(List<Layout> layouts) {
    List<Widget> widgets =
        layouts.stream()
            .flatMap(layout -> layout.getContainers().stream())
            .flatMap(container -> container.getWidgets().stream())
            .filter(LayoutController::isPropertyOrGroupWidget)
            .collect(Collectors.toList());
    return getPropertyIdsFromWidgets(widgets);
  }

  private static boolean isPropertyOrGroupWidget(Widget widget) {
    return widget instanceof PropertyWidget || widget instanceof GroupWidget;
  }

  private static List<UUID> getPropertyIdsFromWidgets(List<Widget> widgets) {
    ArrayList<UUID> propertyIds = new ArrayList<>();
    for (Widget widget : widgets) {
      if (widget instanceof PropertyWidget) {
        var propertyWidget = (PropertyWidget) widget;
        propertyIds.add(propertyWidget.getPropertyId());
      } else if (widget instanceof GroupWidget) {
        var groupWidget = (GroupWidget) widget;
        propertyIds.addAll(getPropertyIdsFromWidgets(groupWidget.getWidgets()));
      }
    }
    return propertyIds;
  }

  private static PropertyResponse getPropertyResponse(Property property) {
    return PropertyResponse.builder()
        .id(property.getId())
        .groupId(property.getPropertyGroupId())
        .type(getPropertyTypeResponse(property.getPropertyType()))
        .name(property.getName())
        .label(property.getLabel())
        .pluralLabel(property.getPluralLabel())
        .description(property.getDescription())
        .build();
  }

  private static PropertyTypeResponse getPropertyTypeResponse(PropertyType propertyType) {
    return PropertyTypeResponse.builder()
        .name(propertyType.getName())
        .label(propertyType.getLabel())
        .semanticType(propertyType.getSemanticType())
        .wireType(propertyType.getWireType())
        .isNumeric(propertyType.isNumeric())
        .isTimestamp(propertyType.isTimestamp())
        .build();
  }
}
