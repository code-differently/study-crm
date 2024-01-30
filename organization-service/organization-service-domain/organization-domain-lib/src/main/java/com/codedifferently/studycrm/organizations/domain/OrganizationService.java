package com.codedifferently.studycrm.organizations.domain;

import org.springframework.security.acls.domain.BasePermission;
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

    @Transactional
    public Organization saveOrganizationAndUser(Organization newOrg, User newUser) {
        Objects.requireNonNull(newOrg);
        Objects.requireNonNull(newUser);

        // Create the user
        User user = userRepository.save(newUser);

        // Create the organization
        Organization organization = organizationRepository.save(newOrg);

        // Save the acls for the user and organization
        addPermission(organization, new PrincipalSid(user.getUuid()), BasePermission.ADMINISTRATION);

        // Return the organization
        return organization;
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