package com.codedifferently.studycrm.organizations.domain;

import org.springframework.util.Assert;

import jakarta.transaction.Transactional;
import java.util.Objects;

public class OrganizationService {

    private OrganizationRepository OrganizationRepository;

    public OrganizationService(OrganizationRepository OrganizationRepository) {
        this.OrganizationRepository = OrganizationRepository;
    }

    @Transactional
    public Organization createOrganization(String organizationName) {
        Organization organization = Organization.builder()
                .name(organizationName)
                .build();
        Objects.requireNonNull(organization);
        return OrganizationRepository.save(organization);
    }
}