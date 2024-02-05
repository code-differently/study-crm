package com.codedifferently.studycrm.auth.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc
class LoginControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private LoginController loginController;

  @Test
  void testLoginController_findLogin() throws Exception {
    this.mockMvc.perform(get("/login")).andExpect(status().is(200));
  }

  @Test
  void testLoginController_redirectsToLogin() throws Exception {
    this.mockMvc
        .perform(get("/test"))
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("http://localhost/login"));
  }

  @Test
  @WithMockUser(username = "myUser", password = "myPass")
  void testLoginController_doesntPermitNormalLogin() throws Exception {
    this.mockMvc
        .perform(formLogin("/login").user("myUser").password("myPass"))
        .andExpect(status().is(405));
  }

  @Test
  void testLoginController_returnsTemplate() throws Exception {
    assertEquals("login", loginController.login());
  }
}
