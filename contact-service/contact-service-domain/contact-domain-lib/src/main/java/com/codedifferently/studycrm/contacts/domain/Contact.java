package com.codedifferently.studycrm.contacts.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Contact")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Contact extends EntityBase {

  private String firstName;

  private String lastName;
}
