package com.codedifferently.studycrm.auth.web.config;

import com.codedifferently.studycrm.auth.web.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {TestConfiguration.class})
class DefaultSecurityConfigTest {

  @Test
  void testDefaultSecurityConfig_loads() throws Exception {
    System.out.println(getClass());
  }
}
