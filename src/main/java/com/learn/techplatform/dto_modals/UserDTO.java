package com.learn.techplatform.dto_modals;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.GenderType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserRole;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.utils.DateUtil;
import com.learn.techplatform.controllers.models.request.EditUserRequest;
import com.learn.techplatform.controllers.models.request.SignUpRequest;
import com.learn.techplatform.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class UserDTO  {
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String bio;
    private GenderType gender;
    @JsonIgnore
    private String passwordHash;
    private String passcode;
    private String username;
    private String profileImage;
    private String coverImage;
    private UserStatus userStatus;
    private SystemStatus systemStatus;
    private String lastIpAddress;
    private Date lastLogin;
    private Long dateOfBirth;
    private UserRole userRole;

    public UserDTO(SignUpRequest signUpRequest) {
        this.email = signUpRequest.getEmail();
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.passwordHash = signUpRequest.getPasswordHash();
    }

    public UserDTO(EditUserRequest editUserRequest) {
        this.firstName = editUserRequest.getFirstName();
        this.lastName = editUserRequest.getLastName();
        this.gender = GenderType.valueOf(editUserRequest.getGender());
        this.phoneNumber = editUserRequest.getPhoneNumber();
        this.dateOfBirth = DateUtil.convertStringDateToLong(editUserRequest.getDateOfBirth());
        this.bio = editUserRequest.getBio();
        this.profileImage = editUserRequest.getProfileImage();
        this.coverImage = editUserRequest.getCoverImage();
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.passwordHash = user.getPasswordHash();
        this.username = user.getUsername();
        this.userStatus = user.getUserStatus();
        this.profileImage = user.getProfileImage();
        this.dateOfBirth = user.getDateOfBirth();
        this.lastIpAddress = user.getLastIpAddress();
        this.lastLogin = user.getLastLogin();
        this.systemStatus = user.getSystemStatus();
        this.userRole = user.getUserRole();
        this.bio = user.getBio();
        this.coverImage = user.getCoverImage();
    }

}
