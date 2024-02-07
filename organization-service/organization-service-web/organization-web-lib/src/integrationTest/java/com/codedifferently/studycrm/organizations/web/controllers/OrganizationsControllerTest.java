package com.codedifferently.studycrm.organizations.web.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.sagas.OrganizationSagaService;
import com.codedifferently.studycrm.organizations.web.TestConfiguration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
public class OrganizationsControllerTest {

  @Autowired private AclPermissionEvaluator aclPermissionEvaluator;

  @Autowired private OrganizationService organizationService;

  @Autowired private OrganizationSagaService organizationSagaService;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    reset(aclPermissionEvaluator, organizationService, organizationSagaService);
  }

  @Test
  @WithMockUser
  void testGetOrganization_WhenNotAuthorized_ReturnsNotAllowed() throws Exception {
    // Arrange
    when(aclPermissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(false);

    // Act
    assertThrows(
        Exception.class,
        () -> mockMvc.perform(get("/organizations/{organizationId}", UUID.randomUUID())));
  }

  @Test
  @WithMockUser
  void testGetOrganization_WhenNotExists_ReturnsNotFound() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    when(aclPermissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(true);
    when(organizationService.findOrganizationById(orgId)).thenReturn(Optional.empty());

    // Act
    mockMvc.perform(get("/organizations/{organizationId}", orgId)).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testGetOrganization_WhenExists_ReturnsOrganization() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    var expected = Organization.builder().id(orgId).name("Test Organization").build();
    when(aclPermissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(true);
    when(organizationService.findOrganizationById(orgId)).thenReturn(Optional.of(expected));

    // Act
    mockMvc
        .perform(get("/organizations/{organizationId}", orgId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.organizationId").value(orgId.toString()))
        .andExpect(jsonPath("$.name").value(expected.getName()));
  }

  @Test
  @WithMockUser
  void testCreateOrganization_WhenNotValid_ReportsErrors() throws Exception {
    // Act
    mockMvc
        .perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .json(
                    "{\"errors\":[\"Organization name is required\",\"User details are required\"]}"));
  }

  @Test
  @WithMockUser
  void testCreateOrganization_WhenValid_ReturnsResponse() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    var expectedOrg = Organization.builder().name("Test Organization").build();
    var expectedUser =
        UserDetails.builder()
            .username("testUser")
            .email("test@user.com")
            .firstName("John")
            .lastName("Doe")
            .build();
    when(organizationSagaService.createOrganization(expectedOrg, expectedUser))
        .then(
            (Answer<Organization>)
                invocation -> {
                  expectedOrg.setId(orgId);
                  return expectedOrg;
                });

    // Act
    mockMvc
        .perform(
            post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"organizationName\":\"Test Organization\",\"userDetails\":{\"username\":\"testUser\"},\"userDetails\":{\"username\":\"testUser\",\"email\":\"test@user.com\",\"firstName\":\"John\",\"lastName\":\"Doe\"}}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.organizationId").value(orgId.toString()));
  }

  @Test
  @WithMockUser
  void testGetAll_ReturnsOrganizations() throws Exception {
    // Arrange
    var expectedOrgs = new ArrayList<Organization>();
    expectedOrgs.add(
        Organization.builder().id(UUID.randomUUID()).name("Test Organization 1").build());
    expectedOrgs.add(
        Organization.builder().id(UUID.randomUUID()).name("Test Organization 2").build());

    when(aclPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(true);
    when(organizationService.findAllOrganizations()).thenReturn(expectedOrgs);

    // Act
    mockMvc
        .perform(get("/organizations"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.organizations[0].organizationId")
                .value(expectedOrgs.get(0).getId().toString()))
        .andExpect(
            jsonPath("$.organizations[1].organizationId")
                .value(expectedOrgs.get(1).getId().toString()));
  }
}
