package com.codedifferently.studycrm.organizations.web;

import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationRequest;
import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationsResponse;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@RestController
public class OrganizationsController {

        private OrganizationService organizationService;
        private OrganizationRepository organizationRepository;

        @Autowired
        public OrganizationsController(OrganizationService organizationService,
                        OrganizationRepository organizationRepository) {
                this.organizationService = organizationService;
                this.organizationRepository = organizationRepository;
        }

        @RequestMapping(value = "/organizations", method = RequestMethod.POST)
        public CreateOrganizationResponse createOrganization(
                        @RequestBody CreateOrganizationRequest createOrganizationRequest) {
                Organization organization = organizationService
                                .createOrganization(createOrganizationRequest.getOrganizationName());
                return new CreateOrganizationResponse(organization.getId());
        }

        @RequestMapping(value = "/organizations", method = RequestMethod.GET)
        public ResponseEntity<GetOrganizationsResponse> getAll(final JwtAuthenticationToken auth) {
                System.out.println("auth: " + auth.getPrincipal());
                return ResponseEntity
                                .ok(new GetOrganizationsResponse(
                                                StreamSupport.stream(organizationRepository.findAll().spliterator(),
                                                                false)
                                                                .map(c -> new GetOrganizationResponse(c.getId(),
                                                                                c.getOrganizationName()))
                                                                .collect(Collectors.toList())));
        }

        @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.GET)
        public ResponseEntity<GetOrganizationResponse> getOrganization(
                        @PathVariable("organizationId") Long organizationId) {
                return organizationRepository
                                .findById(organizationId)
                                .map(c -> new ResponseEntity<>(
                                                new GetOrganizationResponse(c.getId(), c.getOrganizationName()),
                                                HttpStatus.OK))
                                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
}
