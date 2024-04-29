package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = """
        SELECT NEW com.learn.techplatform.dto_modals.UserDTO(u)
        FROM User u
        WHERE u.email = :email AND u.systemStatus = :systemStatus AND u.userStatus = :userStatus
    """)
    UserDTO getUserByEmailAndStatus(@Param("email") String email, @Param("systemStatus") SystemStatus status, @Param("userStatus") UserStatus userStatus);

    @Query(value = """
        SELECT NEW com.learn.techplatform.dto_modals.UserDTO(u)
        FROM User u
        WHERE u.id = :userId AND u.systemStatus = 'ACTIVE'
    """)
    UserDTO getAuthInfo(@Param("userId") String id);

    User findByEmailAndSystemStatus(String email, SystemStatus status);
    User findByEmailAndSystemStatusAndUserStatus(String email, SystemStatus status, UserStatus userStatus);

    User findByIdAndSystemStatusAndUserStatus(String id, SystemStatus status, UserStatus userStatus);
    boolean existsByEmailAndSystemStatusAndUserStatus(String email, SystemStatus systemStatus, UserStatus userStatus);
}
