package com.codedifferently.studycrm.organizations.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Organization")
@Access(AccessType.FIELD)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String organizationName;

    @Version
    private Long version;

    public Organization() {
    }

    public Organization(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long getId() {
        return id;
    }

    public String getOrganizationName() {
        return organizationName;
    }
}