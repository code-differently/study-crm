package com.codedifferently.studycrm.contacts.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Contact")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends EntityBase {

    private String firstName;

    private String lastName;

}