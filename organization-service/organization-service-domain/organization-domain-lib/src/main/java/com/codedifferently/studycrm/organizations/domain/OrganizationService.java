package com.codedifferently.studycrm.organizations.domain;

import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import jakarta.transaction.Transactional;

public class OrganizationService {

    private UserRepository userRepository;
    private OrganizationRepository organizationRepository;
    private MutableAclService mutableAclService;

    public OrganizationService(
            MutableAclService mutableAclService, UserRepository userRepository,
            OrganizationRepository OrganizationRepository) {
        this.mutableAclService = mutableAclService;
        this.userRepository = userRepository;
        this.organizationRepository = OrganizationRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @PreAuthorize("hasPermission(#id, @orgVars.ORGANIZATION_TYPE, read)")
    public Optional<Organization> findOrganizationById(@NonNull @Param("id") String id) {
        return organizationRepository.findById(id);
    }

    @PostFilter("hasPermission(filterObject, read)")
    public Iterable<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    public User saveUser(@NonNull User user) {
        return userRepository.save(user);
    }

    public void activateOrganization(@NonNull String id) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        organization.setActive(true);
        organizationRepository.save(organization);
    }

    public Organization saveOrganization(@NonNull Organization newOrg) {
        return organizationRepository.save(newOrg);
    }

    @Transactional
    public void grantUserOrganizationAccess(
            @NonNull User user, @NonNull Organization organization,
            OrgRolePermission permission) {
        // Save the acls for the user and organization
        createRoleAcl(organization, permission);
    }

    private void createRoleAcl(Organization organization, OrgRolePermission role) {
        var orgRolePricipal = new GrantedAuthoritySid(
                String.format("ROLE_org:%s:%s", organization.getId(), role.name().toLowerCase()));
        addPermission(organization, orgRolePricipal, role.getPermission());
    }

    private void addPermission(Organization organization, Sid recipient, Permission permission) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(Organization.class, organization.getId());

        try {
            acl = (MutableAcl) this.mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = this.mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        this.mutableAclService.updateAcl(acl);
    }
}