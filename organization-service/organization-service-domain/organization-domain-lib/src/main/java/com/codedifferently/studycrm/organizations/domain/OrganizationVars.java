package com.codedifferently.studycrm.organizations.domain;

import org.springframework.stereotype.Component;

@Component("orgVars")
public class OrganizationVars {
    public final String ORGANIZATION_TYPE = Organization.class.getName();
}
