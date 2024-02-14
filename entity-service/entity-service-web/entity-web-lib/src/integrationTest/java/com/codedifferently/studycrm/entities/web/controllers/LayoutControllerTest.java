package com.codedifferently.studycrm.entities.web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codedifferently.studycrm.entities.api.web.*;
import com.codedifferently.studycrm.entities.layout.domain.*;
import com.codedifferently.studycrm.entities.web.TestConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
class LayoutControllerTest {

  @Autowired private LayoutService layoutService;

  private static MockMvc mockMvc;

  @BeforeAll
  static void setUp(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @BeforeEach
  void beforeEach() {
    reset(layoutService);
  }

  @Test
  void getAll_ReturnsLayoutsResponse() throws Exception {
    // Arrange
    UUID orgId = UUID.randomUUID();
    String entityType = "exampleEntityType";
    List<Layout> layouts = createMockLayouts();
    when(layoutService.findAllByEntityType(entityType)).thenReturn(layouts);

    // Act
    mockMvc
        .perform(
            get("/organizations/{orgId}/layouts?entityType={entityType}", orgId, entityType)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    // Assert
    // GetLayoutsResponse responseBody = response.getBody();
    // assertEquals(layouts.size(), responseBody.getLayouts().size());
    // for (int i = 0; i < layouts.size(); i++) {
    //   Layout layout = layouts.get(i);
    //   GetLayoutResponse expectedLayoutResponse = createExpectedLayoutResponse(layout);
    //   GetLayoutResponse actualLayoutResponse = responseBody.getLayouts().get(i);
    //   assertEquals(expectedLayoutResponse, actualLayoutResponse);
    // }
    verify(layoutService, times(1)).findAllByEntityType(entityType);
  }

  private List<Layout> createMockLayouts() {
    List<Layout> layouts = new ArrayList<>();
    Layout layout1 = new Layout();
    layout1.setId(UUID.randomUUID());
    layout1.setEntityType("exampleEntityType");
    layout1.setContainers(createMockContainers());
    layouts.add(layout1);
    // Add more mock layouts if needed
    return layouts;
  }

  private List<Container> createMockContainers() {
    List<Container> containers = new ArrayList<>();
    Container container1 = new Container();
    container1.setWidgets(createMockWidgets());
    containers.add(container1);
    // Add more mock containers if needed
    return containers;
  }

  private List<Widget> createMockWidgets() {
    List<Widget> widgets = new ArrayList<>();
    Widget widget1 = new Widget();
    widget1.setLabel("Example Label");
    widget1.setHideLabel(false);
    widget1.setDisplayOrder(1);
    widgets.add(widget1);
    // Add more mock widgets if needed
    return widgets;
  }

  private GetLayoutResponse createExpectedLayoutResponse(Layout layout) {
    GetLayoutResponse layoutResponse = new GetLayoutResponse();
    layoutResponse.setId(layout.getId());
    layoutResponse.setOrganizationId(layout.getOrganizationId());
    layoutResponse.setEntityType(layout.getEntityType());
    layoutResponse.setContainers(createExpectedContainerResponses(layout.getContainers()));
    return layoutResponse;
  }

  private List<ContainerResponse> createExpectedContainerResponses(List<Container> containers) {
    List<ContainerResponse> containerResponses = new ArrayList<>();
    for (Container container : containers) {
      ContainerResponse containerResponse = new ContainerResponse();
      containerResponse.setWidgets(createExpectedWidgetResponses(container.getWidgets()));
      containerResponses.add(containerResponse);
    }
    return containerResponses;
  }

  private List<WidgetResponse> createExpectedWidgetResponses(List<Widget> widgets) {
    List<WidgetResponse> widgetResponses = new ArrayList<>();
    for (Widget widget : widgets) {
      WidgetResponse widgetResponse;
      if (widget instanceof GroupWidget) {
        GroupWidgetResponse groupWidgetResponse = new GroupWidgetResponse();
        groupWidgetResponse.setPropertyGroupId(((GroupWidget) widget).getPropertyGroupId());
        groupWidgetResponse.setWidgets(
            createExpectedWidgetResponses(((GroupWidget) widget).getWidgets()));
        widgetResponse = groupWidgetResponse;
      } else if (widget instanceof PropertyWidget) {
        PropertyWidgetResponse propertyWidgetResponse = new PropertyWidgetResponse();
        propertyWidgetResponse.setPropertyId(((PropertyWidget) widget).getPropertyId());
        widgetResponse = propertyWidgetResponse;
      } else {
        widgetResponse = new WidgetResponse();
      }
      widgetResponse.setLabel(widget.getLabel());
      widgetResponse.setHideLabel(widget.isHideLabel());
      widgetResponse.setDisplayOrder(widget.getDisplayOrder());
      widgetResponses.add(widgetResponse);
    }
    return widgetResponses;
  }
}
