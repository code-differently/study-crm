package com.codedifferently.studycrm.organizations.api.web;

public class CreateOrganizationRequest {
    private String organizationName;

    public CreateOrganizationRequest() {
    }

    public CreateOrganizationRequest(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }
}