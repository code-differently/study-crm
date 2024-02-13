package com.codedifferently.studycrm.organizations.web.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.User;
import com.codedifferently.studycrm.organizations.web.TestConfiguration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
class UserInfoControllerTest {

  @Autowired private OrganizationService organizationService;

  private static MockMvc mockMvc;

  @BeforeAll
  static void setUp(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
  }

  @BeforeEach
  void beforeEach() {
    reset(organizationService);
  }

  @Test
  @WithMockUser(username = "testuser@studycrm.com")
  void getUserInfo_ShouldReturnUserInfoResponse() throws Exception {
    User user = new User();
    user.setUsername("testuser@studycrm.com");
    user.setEmail("testuser@example.com");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setDefaultOrganizationId(UUID.randomUUID());

    when(organizationService.findUserByUsername(anyString())).thenReturn(Optional.of(user));

    mockMvc
        .perform(get("/user").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(jsonPath("$.email").value(user.getEmail()))
        .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(user.getLastName()))
        .andExpect(
            jsonPath("$.defaultOrganizationId").value(user.getDefaultOrganizationId().toString()))
        .andExpect(jsonPath("$.organizationIds").isEmpty());

    verify(organizationService).findUserByUsername(anyString());
  }

  @Test
  @WithAnonymousUser
  void getUserInfo_WithInvalidAuthentication_IsUnauthorized() throws Exception {
    when(organizationService.findUserByUsername(anyString())).thenReturn(Optional.empty());

    mockMvc.perform(get("/user")).andExpect(status().isUnauthorized());

    verify(organizationService, times(0));
  }

  @Test
  @WithMockUser(username = "testuser@studycrm.com")
  void getUserInfo_WithMissingUser_IsNotFound() throws Exception {
    when(organizationService.findUserByUsername(anyString())).thenReturn(Optional.empty());

    mockMvc.perform(get("/user")).andExpect(status().isNotFound());

    verify(organizationService).findUserByUsername(anyString());
  }
}
