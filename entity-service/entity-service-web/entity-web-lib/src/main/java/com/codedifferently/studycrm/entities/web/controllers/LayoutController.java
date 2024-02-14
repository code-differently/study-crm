package com.codedifferently.studycrm.entities.web.controllers;

import com.codedifferently.studycrm.entities.api.web.*;
import com.codedifferently.studycrm.entities.layout.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/organizations/{organizationId}/layouts")
public class LayoutController {

    @Autowired private LayoutService layoutService;

    @GetMapping
    public ResponseEntity<GetLayoutsResponse> getAll(@RequestParam("entityType") String entityType) {
        List<Layout> layouts = layoutService.findAllByEntityType(entityType);
        List<GetLayoutResponse> layoutResponses = layouts.stream()
                .map(layout -> GetLayoutResponse.builder()
                    .id(layout.getId())
                    .organizationId(layout.getOrganizationId())
                    .entityType(layout.getEntityType())
                    .containers(layout.getContainers().stream()
                        .map(container -> ContainerResponse.builder()
                            .widgets(getWidgetResponses(container.getWidgets()))
                            .build())
                        .collect(Collectors.toList()))
                    .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(GetLayoutsResponse.builder()
            .layouts(layoutResponses).build());
    }

    private List<WidgetResponse> getWidgetResponses(List<Widget> widgets) {
        var responses = new ArrayList<WidgetResponse>();

        for (Widget widget : widgets) {
            var builder = WidgetResponse.builder();

            if (widget instanceof GroupWidget) {
                var groupWidget = (GroupWidget) widget;
                builder = GroupWidgetResponse.builder()
                    .propertyGroupId(groupWidget.getPropertyGroupId())
                    .widgets(getWidgetResponses(groupWidget.getWidgets()));
            } else if (widget instanceof FieldWidget) {
                var fieldWidget = (FieldWidget) widget;
                builder = FieldWidgetResponse.builder()
                    .propertyId(fieldWidget.getPropertyId());
            }

            responses.add(builder
                .label(widget.getLabel())
                .hideLabel(widget.isHideLabel())
                .displayOrder(widget.getDisplayOrder())
                .build());
        }
        
        return responses;
    }
    
}