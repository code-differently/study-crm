package com.codedifferently.studycrm.auth.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "[user]")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class User extends EntityBase {

  @Column(nullable = false, unique = true)
  @NotBlank(message = "Username is required")
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Builder.Default
  private String firstName = "";

  @Column(nullable = false)
  @Builder.Default
  private String lastName = "";

  @Column(nullable = false, unique = true)
  @NotBlank(message = "Email is required")
  private String email;

  @Column(nullable = false)
  @Builder.Default
  private boolean isAccountEnabled = true;

  @Column(nullable = false)
  @Builder.Default
  private boolean isAccountLocked = false;

  @OneToMany(
      mappedBy = "user",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<UserAuthority> authorities;
}
