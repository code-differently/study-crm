package com.codedifferently.studycrm.organizations.api.web;

public class CreateOrganizationResponse {
    private Long organizationId;

    public CreateOrganizationResponse() {
    }

    public CreateOrganizationResponse(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}