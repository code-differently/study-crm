package com.codedifferently.studycrm.organizations.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationRequest;
import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationsResponse;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;
import com.codedifferently.studycrm.organizations.sagas.OrganizationSagaService;

@RestController
public class OrganizationsController {

	private OrganizationSagaService organizationSagaService;
	private OrganizationRepository organizationRepository;

	public OrganizationsController(OrganizationSagaService organizationSagaService,
			OrganizationRepository organizationRepository) {
		this.organizationSagaService = organizationSagaService;
		this.organizationRepository = organizationRepository;
	}

	@PostMapping(value = "/organizations")
	public CreateOrganizationResponse createOrganization(
			@RequestBody CreateOrganizationRequest createOrganizationRequest) {
		var newOrg = Organization.builder()
				.name(createOrganizationRequest.getOrganizationName())
				.build();
		UserDetails userDetails = createOrganizationRequest.getUserDetails();
		Organization organization = organizationSagaService.createOrganization(newOrg, userDetails);
		return new CreateOrganizationResponse(organization.getUuid());
	}

	@GetMapping(value = "/organizations")
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

	@GetMapping(value = "/organizations/{organizationId}")
	@PreAuthorize("hasPermission(#organizationId, 'com.codedifferently.studycrm.organizations.domain.Organization', read)")
	public ResponseEntity<GetOrganizationResponse> getOrganization(
			@PathVariable("organizationId") @Param("organizationId") String organizationId) {
		return organizationRepository
				.findById(organizationId)
				.map(c -> new ResponseEntity<>(
						new GetOrganizationResponse(c.getUuid(), c.getName()),
						HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
