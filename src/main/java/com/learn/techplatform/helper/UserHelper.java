package com.learn.techplatform.helper;

import com.google.firebase.auth.UserRecord;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserRole;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.utils.PasswordHash;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {

    public User createUser(UserDTO userDTO, PasswordHash passwordHash) {
        return User.builder()
                .id(UniqueID.getUUID())
                .email(userDTO.getEmail())
                .passwordHash(passwordHash.getPasswordHash())
                .passwordSalt(passwordHash.getPasswordSalt())
                .systemStatus(SystemStatus.ACTIVE)
                .userRole(UserRole.USER)
                .userStatus(UserStatus.PENDING)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username("user" + UniqueID.getUUID())
                .build();
    }

    public User createUser(UserRecord userRecord) {
        return User.builder()
                .id(UniqueID.getUUID())
                .userRole(UserRole.USER)
                .systemStatus(SystemStatus.ACTIVE)
                .firstName(userRecord.getDisplayName())
                .is2Fa(false)
                .username("user" + UniqueID.generateKey(5))
                .userStatus(UserStatus.ACTIVE)
                .email(userRecord.getEmail())
                .profileImage(userRecord.getPhotoUrl())
                .phoneNumber(userRecord.getPhoneNumber())
                .build();
    }
}
