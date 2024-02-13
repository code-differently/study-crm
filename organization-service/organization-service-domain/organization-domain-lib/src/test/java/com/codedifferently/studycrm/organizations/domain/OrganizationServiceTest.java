package com.codedifferently.studycrm.organizations.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.codedifferently.studycrm.common.domain.EntityAclManager;
import com.codedifferently.studycrm.common.domain.RolePermission;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = OrganizationDomainTestConfiguration.class)
class OrganizationServiceTest {

  @Autowired private UserRepository userRepository;

  @Autowired private OrganizationRepository organizationRepository;

  @Autowired private PermissionEvaluator permissionEvaluator;

  @Autowired private EntityAclManager entityAclManager;

  @Autowired private OrganizationService organizationService;

  @BeforeEach
  void setUp() {
    reset(permissionEvaluator);
    reset(entityAclManager);
    reset(userRepository);
    reset(organizationRepository);
  }

  @Test
  @WithMockUser
  void testFindUserByUsername() {
    // Arrange
    var user = User.builder().username("testUser").build();
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

    // Act
    var result = organizationService.findUserByUsername("testUser");

    // Assert
    assertEquals(user, result, "The user should be returned");
  }

  @Test
  @WithMockUser
  void testFindOrganizationById_notAuthorized() {
    // Act & Assert
    assertThrows(
        AccessDeniedException.class,
        () -> organizationService.findOrganizationById(UUID.randomUUID()));
  }

  @Test
  @WithMockUser
  void testFindOrganizationById_findById() {
    // Arrange
    var id = UUID.randomUUID();
    var organization = Organization.builder().id(id).build();
    when(permissionEvaluator.hasPermission(
            any(), eq(id), eq(Organization.class.getName()), eq("read")))
        .thenReturn(true);
    when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));

    // Act
    Optional<Organization> result = organizationService.findOrganizationById(id);

    // Assert
    assertTrue(result.isPresent(), "The organization should be returned");
    assertEquals(organization, result.get(), "The organization should be returned");
  }

  @Test
  @WithMockUser
  void testFindAllOrganizations_notAuthorized() {
    // Arrange
    var organizations = new ArrayList<Organization>();
    var organization1 = Organization.builder().id(UUID.randomUUID()).build();
    var organization2 = Organization.builder().id(UUID.randomUUID()).build();
    organizations.add(organization1);
    organizations.add(organization2);
    when(organizationRepository.findAll()).thenReturn(organizations);

    // Act
    Iterable<Organization> results = organizationService.findAllOrganizations();

    // Assert
    assertFalse(results.iterator().hasNext(), "No organizations should be returned");
  }

  @Test
  @WithMockUser
  void testFindAllOrganizations() {
    // Arrange
    var organizations = new ArrayList<Organization>();
    var organization1 = Organization.builder().id(UUID.randomUUID()).build();
    var organization2 = Organization.builder().id(UUID.randomUUID()).build();
    organizations.add(organization1);
    organizations.add(organization2);

    when(permissionEvaluator.hasPermission(any(), eq(organization2), eq("read"))).thenReturn(true);
    when(organizationRepository.findAll()).thenReturn(organizations);

    // Act
    Iterable<Organization> results = organizationService.findAllOrganizations();

    // Assert
    var resultsList = new ArrayList<Organization>();
    results.forEach(resultsList::add);
    assertEquals(1, resultsList.size(), "Only one organization should be returned");
    assertEquals(organization2, resultsList.get(0), "The organization should be returned");
  }

  @Test
  void testSaveUser() {
    // Arrange
    var user = User.builder().id(UUID.randomUUID()).build();
    when(userRepository.save(user)).thenReturn(user);

    // Act
    var result = organizationService.saveUser(user);

    // Assert
    assertEquals(user, result, "The user should be returned");
    verify(userRepository).save(user);
  }

  @Test
  void testActivateOrganization() {
    // Arrange
    var organization = Organization.builder().id(UUID.randomUUID()).build();
    when(organizationRepository.findById(any())).thenReturn(Optional.of(organization));

    // Act
    organizationService.activateOrganization(organization.getId());

    // Assert
    verify(organizationRepository).save(organization);
    assertTrue(organization.isActive(), "The organization should be active");
  }

  @Test
  void testActivateOrganization_throwsIfNotFound() {
    // Arrange
    var organization = Organization.builder().id(UUID.randomUUID()).build();
    when(organizationRepository.findById(any())).thenReturn(Optional.empty());

    // Act
    assertThrows(
        NotFoundException.class,
        () -> organizationService.activateOrganization(organization.getId()),
        "The organization should not be found");
  }

  @Test
  void testSaveOrganization() {
    // Arrange
    var organization = Organization.builder().id(UUID.randomUUID()).build();
    when(organizationRepository.save(organization)).thenReturn(organization);

    // Act
    var result = organizationService.saveOrganization(organization);

    // Assert
    assertEquals(organization, result, "The organization should be returned");
    verify(organizationRepository).save(organization);
  }

  @Test
  void testGrantUserOrganizationAccess() {
    // Arrange
    var user = User.builder().id(UUID.randomUUID()).build();
    var organization =
        Organization.builder().id(UUID.fromString("1843dff3-07ae-4ad1-8ebc-edb4502cb4b6")).build();

    // Act
    organizationService.grantUserOrganizationAccess(user, organization, RolePermission.EDITOR);

    // Assert
    verify(entityAclManager)
        .addPermission(
            organization,
            new GrantedAuthoritySid("ROLE_org:1843dff3-07ae-4ad1-8ebc-edb4502cb4b6:editor"),
            RolePermission.EDITOR.getPermission());
  }
}
