package com.codedifferently.studycrm.organizations.domain;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

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

    public User saveUser(User user) {
        Objects.requireNonNull(user);
        return userRepository.save(user);
    }

    public void activateOrganization(String id) {
        Objects.requireNonNull(id);
        Organization organization = organizationRepository.findById(id).orElse(null);
        organization.setActive(true);
        organizationRepository.save(organization);
    }

    public Organization saveOrganization(Organization newOrg) {
        Objects.requireNonNull(newOrg);
        return organizationRepository.save(newOrg);
    }

    @Transactional
    public void grantUserOrganizationAccess(User user, Organization organization, Permission permission) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(organization);

        // Save the acls for the user and organization
        addPermission(organization, new PrincipalSid(user.getUuid()), permission);
    }

    public void addPermission(Organization organization, Sid recipient, Permission permission) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(Organization.class, organization.getUuid());

        try {
            acl = (MutableAcl) this.mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = this.mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        this.mutableAclService.updateAcl(acl);
    }
}