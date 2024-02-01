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

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String uuid;

    private String name;

    private boolean isActive;

    public String getId() {
        return uuid;
    }
}