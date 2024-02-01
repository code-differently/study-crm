package com.codedifferently.studycrm.organizations.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, UUID> {}
