package com.codedifferently.studycrm.auth.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

  private static MockMvc mockMvc;

  @BeforeAll
  static void setUp(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
  }

  @Test
  void testLoginController_findLogin() throws Exception {
    mockMvc.perform(get("/login")).andExpect(status().is(200));
  }

  @Test
  void testLoginController_redirectsToLogin() throws Exception {
    mockMvc
        .perform(get("/test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/oauth2/authorization/google-idp"));
  }

  @Test
  @WithMockUser(username = "myUser", password = "myPass")
  void testLoginController_doesntPermitNormalLogin() throws Exception {
    mockMvc
        .perform(formLogin("/login").user("myUser").password("myPass"))
        .andExpect(status().is(405));
  }
}
