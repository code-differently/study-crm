package com.codedifferently.studycrm.organizations.web;

import com.codedifferently.studycrm.organizations.domain.Organization;

import org.springframework.stereotype.Component;

@Component("orgVars")
public class OrganizationVars {
    public final String ORGANIZATION_TYPE = Organization.class.getName();
}
