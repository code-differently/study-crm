package com.codedifferently.studycrm.organizations.api.web;

import com.codedifferently.studycrm.organizations.api.web.GetOrganizationResponse;

import java.util.List;

public class GetOrganizationsResponse {
    private List<GetOrganizationResponse> organizations;

    public GetOrganizationsResponse() {
    }

    public GetOrganizationsResponse(List<GetOrganizationResponse> organizations) {
        this.organizations = organizations;
    }

    public List<GetOrganizationResponse> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<GetOrganizationResponse> organizations) {
        this.organizations = organizations;
    }
}
