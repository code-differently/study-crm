package com.codedifferently.studycrm.organizations.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "Organization")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends EntityBase {

    private String name;

    private boolean isActive;

}