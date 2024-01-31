package com.codedifferently.studycrm.auth.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "User")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends EntityBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String uuid;

    @Column(nullable = false, unique = true)
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
    private String email;

    @Column(nullable = false)
    @Builder.Default
    private boolean isAccountEnabled = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean isAccountLocked = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAuthority> authorities;

}