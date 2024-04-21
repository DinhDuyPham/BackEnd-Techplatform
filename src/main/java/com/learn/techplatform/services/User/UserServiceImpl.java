package com.learn.techplatform.services.User;

import com.learn.techplatform.common.enums.GenderType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.request.EditUserRequest;
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

    @Override
    public void editUserInfo(String id, UserDTO userDTO) {
        User user = userRepository.findByIdAndSystemStatusAndUserStatus(id, SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.NOT_FOUND, RestStatusMessage.USER_NOT_FOUND);

        this.save(user);
    }

    @Override
    public void deleteAccount(String id) {
        User user = userRepository.findByIdAndSystemStatusAndUserStatus(id, SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.NOT_FOUND, RestStatusMessage.USER_NOT_FOUND);
        user.setSystemStatus(SystemStatus.INACTIVE);
        user.setUserStatus(UserStatus.INACTIVE);
        this.save(user);
    }
}
