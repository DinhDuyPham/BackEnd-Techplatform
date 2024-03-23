package com.learn.techplatform.services.User;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.repositories.UserRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractBaseService<User, String> implements UserService {

    @Autowired
    UserRepository userRepository;

    public UserServiceImpl(JpaRepository<User, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.getUserByEmailAndStatus(email, SystemStatus.ACTIVE, UserStatus.ACTIVE);
    }

    @Override
    public UserDTO getAuthInfo(String id) {
        return userRepository.getAuthInfo(id);
    }
}
