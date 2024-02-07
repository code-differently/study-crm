package com.codedifferently.studycrm.organizations.web.config;

import com.codedifferently.studycrm.organizations.web.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {TestConfiguration.class})
class OrganizationsWebConfigurationTest {

  @Test
  void testWebConfiguration_loads() throws Exception {
    // no-op: Just confirms that the config loaded successfully.
  }
}
