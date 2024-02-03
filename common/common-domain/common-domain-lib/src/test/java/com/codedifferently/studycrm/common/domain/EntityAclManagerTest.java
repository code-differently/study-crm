package com.codedifferently.studycrm.common.domain;

import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

class EntityAclManagerTest {

  private EntityAclManager entityAclManager;
  private MutableAclService mutableAclService;
  private MutableAcl mutableAcl;
  private EntityBase target;
  private Sid recipient;
  private Permission permission;

  @BeforeEach
  void setUp() {
    mutableAclService = mock(MutableAclService.class);
    mutableAcl = mock(MutableAcl.class);
    entityAclManager = new EntityAclManager(mutableAclService);
    target = EntityBaseImpl.builder().id(UUID.randomUUID()).build();
    recipient = mock(Sid.class);
    permission = mock(Permission.class);
  }

  @Test
  void testAddPermission_ExistingAcl() {
    // Arrange
    ObjectIdentity oid = new ObjectIdentityImpl(target.getClass(), target.getId());
    when(mutableAclService.readAclById(oid)).thenReturn(mutableAcl);

    // Act
    entityAclManager.addPermission(target, recipient, permission);

    // Assert
    verify(mutableAcl).insertAce(anyInt(), eq(permission), eq(recipient), eq(true));
    verify(mutableAclService).updateAcl(mutableAcl);
  }

  @Test
  void testAddPermission_NonExistingAcl() {
    // Arrange
    ObjectIdentity oid = new ObjectIdentityImpl(target.getClass(), target.getId());
    when(mutableAclService.readAclById(oid)).thenThrow(NotFoundException.class);
    when(mutableAclService.createAcl(oid)).thenReturn(mutableAcl);

    // Act
    entityAclManager.addPermission(target, recipient, permission);

    // Assert
    verify(mutableAclService).createAcl(oid);
    verify(mutableAcl).insertAce(anyInt(), eq(permission), eq(recipient), eq(true));
    verify(mutableAclService).updateAcl(mutableAcl);
  }
}
