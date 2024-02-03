package com.codedifferently.studycrm.organizations.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = OrganizationService.class)
@EnableMethodSecurity()
public class OrganizationDomainConfiguration {}
