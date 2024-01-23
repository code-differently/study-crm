package com.codedifferently.studycrm.organizations.api.web;

public class GetOrganizationResponse {
    private long organizationId;
    private String organizationName;

    public GetOrganizationResponse() {
    }

    public GetOrganizationResponse(long organizationId, String organizationName) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
