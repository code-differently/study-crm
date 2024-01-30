package com.codedifferently.studycrm.auth.domain;

import com.codedifferently.studycrm.common.domain.EntityBase;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserAuthority")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority extends EntityBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String uuid;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String authority;

}
