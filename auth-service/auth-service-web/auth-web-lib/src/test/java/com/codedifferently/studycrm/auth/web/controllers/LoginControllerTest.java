/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.codedifferently.studycrm.auth.web.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.codedifferently.studycrm.auth.web.config.AuthServerSecurityConfig;
import com.codedifferently.studycrm.auth.web.config.DefaultSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(LoginController.class)
@ContextConfiguration(
    classes = {
      AuthWebTestConfiguration.class,
      AuthServerSecurityConfig.class,
      DefaultSecurityConfig.class
    })
class LoginControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testLoginController_redirectsToLogin() throws Exception {
    this.mockMvc
      .perform(get("/test"))
      .andExpect(status().is(302))
      .andExpect(redirectedUrl("http://localhost/login"));
  }
}
