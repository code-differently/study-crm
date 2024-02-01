package com.codedifferently.studycrm.organizations.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<Organization, UUID> {
}