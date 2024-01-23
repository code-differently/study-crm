package com.codedifferently.studycrm.organizations.domain;

import jakarta.transaction.Transactional;

public class OrganizationService {

    private OrganizationRepository OrganizationRepository;

    public OrganizationService(OrganizationRepository OrganizationRepository) {
        this.OrganizationRepository = OrganizationRepository;
    }

    @Transactional
    public Organization createOrganization(String organizationName) {
        Organization Organization = new Organization(organizationName);
        return OrganizationRepository.save(Organization);
    }
}