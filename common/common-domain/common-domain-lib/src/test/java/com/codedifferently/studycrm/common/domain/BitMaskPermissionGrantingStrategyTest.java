package com.codedifferently.studycrm.common.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.CumulativePermission;
import org.springframework.security.acls.model.AccessControlEntry;

class BitMaskPermissionGrantingStrategyTest {

  private BitMaskPermissionGrantingStrategy strategy;
  private AccessControlEntry ace;

  @BeforeEach
  void setUp() {
    strategy = new BitMaskPermissionGrantingStrategy(mock(AuditLogger.class));
    ace = mock(AccessControlEntry.class);
  }

  @Test
  void testIsGranted_WhenAceIsGrantingAndPermissionMaskIsNonZero_ShouldReturnTrue() {
    when(ace.isGranting()).thenReturn(true);
    when(ace.getPermission())
        .thenReturn(new CumulativePermission().set(BasePermission.READ).set(BasePermission.WRITE));

    assertTrue(strategy.isGranted(ace, BasePermission.READ));
  }

  @Test
  void testIsGranted_WhenAceIsNotGrantingAndPermissionMaskIsEqual_ShouldReturnTrue() {
    when(ace.isGranting()).thenReturn(false);
    when(ace.getPermission()).thenReturn(new CumulativePermission().set(BasePermission.WRITE));

    assertTrue(strategy.isGranted(ace, BasePermission.WRITE));
  }

  @Test
  void testIsGranted_WhenAceIsGrantingAndPermissionMaskIsZero_ShouldReturnFalse() {
    when(ace.isGranting()).thenReturn(true);
    when(ace.getPermission())
        .thenReturn(new CumulativePermission().set(BasePermission.READ).set(BasePermission.WRITE));

    assertFalse(strategy.isGranted(ace, BasePermission.ADMINISTRATION));
  }

  @Test
  void testIsGranted_WhenAceIsNotGrantingAndPermissionMaskIsNonZero_ShouldReturnFalse() {
    when(ace.isGranting()).thenReturn(false);
    when(ace.getPermission())
        .thenReturn(new CumulativePermission().set(BasePermission.READ).set(BasePermission.WRITE));

    assertFalse(strategy.isGranted(ace, BasePermission.ADMINISTRATION));
  }
}
