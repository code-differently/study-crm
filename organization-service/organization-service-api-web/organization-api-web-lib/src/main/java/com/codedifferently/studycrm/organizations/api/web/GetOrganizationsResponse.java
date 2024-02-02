package com.codedifferently.studycrm.organizations.api.web;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class GetOrganizationsResponse {

  private List<GetOrganizationResponse> organizations;
}
