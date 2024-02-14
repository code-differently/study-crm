package com.codedifferently.studycrm.entities.web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    var result =
        mockMvc.perform(
            get("/organizations/{orgId}/layouts?entityType={entityType}", orgId, entityType)
                .contentType(MediaType.APPLICATION_JSON));

    // Assert
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.layouts").isArray())
        .andExpect(jsonPath("$.layouts[0].entityType").value("exampleEntityType"))
        .andExpect(jsonPath("$.layouts[0].containers").isArray())
        .andExpect(jsonPath("$.layouts[0].containers[0].widgets").isArray())
        .andExpect(jsonPath("$.layouts[0].containers[0].widgets[0].type").value("my_group_widget"))
        .andExpect(jsonPath("$.layouts[0].containers[0].widgets[0].label").value("Example Label"))
        .andExpect(jsonPath("$.layouts[0].containers[0].widgets[0].displayOrder").value(3))
        .andExpect(jsonPath("$.layouts[0].containers[0].widgets[0].widgets").isArray())
        .andExpect(
            jsonPath("$.layouts[0].containers[0].widgets[0].widgets[0].type")
                .value("my_property_widget"))
        .andExpect(
            jsonPath("$.layouts[0].containers[0].widgets[0].widgets[0].label")
                .value("Another Example Label"))
        .andExpect(
            jsonPath("$.layouts[0].containers[0].widgets[0].widgets[0].displayOrder").value(1));
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

    var childWidget = new PropertyWidget();
    childWidget.setType("my_property_widget");
    childWidget.setLabel("Another Example Label");
    childWidget.setHideLabel(false);
    childWidget.setDisplayOrder(1);

    var groupWidget = new GroupWidget();
    groupWidget.setType("my_group_widget");
    groupWidget.setLabel("Example Label");
    groupWidget.setHideLabel(true);
    groupWidget.setDisplayOrder(3);
    groupWidget.setWidgets(List.of(childWidget));
    widgets.add(groupWidget);

    // Add more mock widgets if needed
    return widgets;
  }
}
