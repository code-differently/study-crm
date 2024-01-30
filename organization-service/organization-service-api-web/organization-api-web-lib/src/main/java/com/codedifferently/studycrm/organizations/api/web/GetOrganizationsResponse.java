package com.codedifferently.studycrm.organizations.api.web;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrganizationsResponse {

    private List<GetOrganizationResponse> organizations;

}
