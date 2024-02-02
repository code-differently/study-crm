package com.codedifferently.studycrm.common.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.acls.domain.BasePermission;

class RolePermissionTest {
  static int READ_MASK = BasePermission.READ.getMask();
  static int CREATE_MASK = BasePermission.CREATE.getMask();
  static int WRITE_MASK = BasePermission.WRITE.getMask();
  static int DELETE_MASK = BasePermission.DELETE.getMask();
  static int ADMIN_MASK = BasePermission.ADMINISTRATION.getMask();

  @Test
  void representsReaderPermissions() {
    // Act
    int mask = RolePermission.READER.getPermission().getMask();

    // Assert
    assertTrue(0 < (mask & READ_MASK));
    assertTrue(0 == (mask & CREATE_MASK));
    assertTrue(0 == (mask & WRITE_MASK));
    assertTrue(0 == (mask & DELETE_MASK));
    assertTrue(0 == (mask & ADMIN_MASK));
  }

  @Test
  void representsWriterPermissions() {
    // Act
    int mask = RolePermission.EDITOR.getPermission().getMask();

    // Assert
    assertTrue(0 < (mask & READ_MASK));
    assertTrue(0 < (mask & CREATE_MASK));
    assertTrue(0 < (mask & WRITE_MASK));
    assertTrue(0 == (mask & DELETE_MASK));
    assertTrue(0 == (mask & ADMIN_MASK));
  }

  @Test
  void representsAdminPermissions() {
    // Act
    int mask = RolePermission.ADMIN.getPermission().getMask();

    // Assert
    assertTrue(0 < (mask & READ_MASK));
    assertTrue(0 < (mask & CREATE_MASK));
    assertTrue(0 < (mask & WRITE_MASK));
    assertTrue(0 < (mask & DELETE_MASK));
    assertTrue(0 < (mask & ADMIN_MASK));
  }
}
