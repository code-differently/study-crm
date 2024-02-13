package com.codedifferently.studycrm.organizations.domain;

import com.codedifferently.studycrm.common.domain.EntityAclManager;
import com.codedifferently.studycrm.common.domain.EntityNotFoundException;
import com.codedifferently.studycrm.common.domain.RolePermission;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

  @Autowired private UserRepository userRepository;

  @Autowired private OrganizationRepository organizationRepository;

  @Autowired private EntityAclManager entityAclManager;

  public User findUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
  }

  @PreAuthorize("hasPermission(#id, @orgVars.ORGANIZATION_TYPE, read)")
  public Optional<Organization> findOrganizationById(@NonNull @Param("id") UUID id) {
    return organizationRepository.findById(id);
  }

  @PostFilter("hasPermission(filterObject, read)")
  public Iterable<Organization> findAllOrganizations() {
    return organizationRepository.findAll();
  }

  public User saveUser(@NonNull User user) {
    return userRepository.save(user);
  }

  public void activateOrganization(@NonNull UUID id) {
    Organization organization = organizationRepository.findById(id).orElse(null);
    if (organization == null) {
      throw new NotFoundException("Organization not found");
    }
    organization.setActive(true);
    organizationRepository.save(organization);
  }

  public Organization saveOrganization(@NonNull Organization newOrg) {
    return organizationRepository.save(newOrg);
  }

  @Transactional
  public void grantUserOrganizationAccess(
      @NonNull User user, @NonNull Organization organization, RolePermission permission) {
    // Save the acls for the user and organization
    createRoleAcl(organization, permission);
  }

  private void createRoleAcl(Organization organization, RolePermission role) {
    var orgRolePricipal =
        new GrantedAuthoritySid(
            String.format("ROLE_org:%s:%s", organization.getId(), role.name().toLowerCase()));
    entityAclManager.addPermission(organization, orgRolePricipal, role.getPermission());
  }
}
