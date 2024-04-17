package com.learn.techplatform.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.learn.techplatform.common.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@EntityListeners(AuditingEntityListener.class)
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "[user]")
public class User extends AbstractBaseEntity<String> implements Serializable {

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "password_hash")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    @Column(name = "password_salt", length = 36)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordSalt;

    @Column(name = "gender", length = 50)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "last_ip_address", length = 45)
    private String lastIpAddress;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "date_of_birth")
    private Long dateOfBirth;

    @Column(name = "user_role", length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "is_2_Fa", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean is2Fa;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "user_status", length = 50)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "cover_image", columnDefinition = "TEXT")
    private String coverImage;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
}
