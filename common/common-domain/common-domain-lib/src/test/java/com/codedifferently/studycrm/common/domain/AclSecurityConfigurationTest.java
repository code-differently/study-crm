package com.codedifferently.studycrm.common.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = AclSecurityConfiguration.class)
public class AclSecurityConfigurationTest {

  @Autowired private PermissionGrantingStrategy permissionGrantingStrategy;

  @Test
  void testConfiguration() {
    assertInstanceOf(BitMaskPermissionGrantingStrategy.class, permissionGrantingStrategy);
  }
}
