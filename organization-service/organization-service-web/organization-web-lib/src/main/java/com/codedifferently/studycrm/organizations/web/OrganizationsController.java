package com.codedifferently.studycrm.organizations.web;

import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationRequest;
import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationsResponse;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;
import com.codedifferently.studycrm.organizations.domain.User;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import java.util.List;
import java.util.Map;

@RestController
public class OrganizationsController {

        private OrganizationService organizationService;
        private OrganizationRepository organizationRepository;

        public OrganizationsController(OrganizationService organizationService,
                        OrganizationRepository organizationRepository) {
                this.organizationService = organizationService;
                this.organizationRepository = organizationRepository;
        }

        @RequestMapping(value = "/organizations", method = RequestMethod.POST)
        public CreateOrganizationResponse createOrganization(
                        @RequestBody CreateOrganizationRequest createOrganizationRequest) {
                var newOrg = Organization.builder()
                                .name(createOrganizationRequest.getOrganizationName())
                                .build();
                UserDetails userDetails = createOrganizationRequest.getUserDetails();
                var newUser = User.builder()
                                .username(userDetails.getUsername())
                                .email(userDetails.getEmail())
                                .password(userDetails.getPassword())
                                .firstName(userDetails.getFirstName())
                                .lastName(userDetails.getLastName())
                                .build();
                Organization organization = organizationService.saveOrganizationAndUser(newOrg, newUser);
                return new CreateOrganizationResponse(organization.getUuid());
        }

        @RequestMapping(value = "/organizations", method = RequestMethod.GET)
        public ResponseEntity<GetOrganizationsResponse> getAll(final JwtAuthenticationToken auth) {
                if (auth != null) {
                        List<String> authorities = auth.getAuthorities().stream()
                                        .map(Object::toString)
                                        .collect(Collectors.toList());

                        System.out.println("Authorities: " + authorities);

                        Map<String, Object> claims = auth.getTokenAttributes();
                        System.out.println("Claims: " + claims);
                }

                return ResponseEntity
                                .ok(new GetOrganizationsResponse(
                                                StreamSupport.stream(organizationRepository.findAll().spliterator(),
                                                                false)
                                                                .map(c -> new GetOrganizationResponse(c.getUuid(),
                                                                                c.getName()))
                                                                .collect(Collectors.toList())));
        }

        @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.GET)
        public ResponseEntity<GetOrganizationResponse> getOrganization(
                        @PathVariable("organizationId") String organizationId) {
                return organizationRepository
                                .findById(organizationId)
                                .map(c -> new ResponseEntity<>(
                                                new GetOrganizationResponse(c.getUuid(), c.getName()),
                                                HttpStatus.OK))
                                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
}
