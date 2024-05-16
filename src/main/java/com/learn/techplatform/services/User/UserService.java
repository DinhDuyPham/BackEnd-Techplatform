package com.learn.techplatform.services.User;

import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.controllers.models.request.EditUserRequest;
import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.services.InterfaceBaseService;

public interface UserService extends InterfaceBaseService<User, String> {
    UserDTO getUserByEmail(String email);
    UserDTO getAuthInfo(String id);
    User getByUsername(String username);
    User getByIdAndUserStatus(String id, UserStatus status);
    UserDTO getAuthInfoFromToken(String authToken);
    void deleteAccount(String id);
    void newUserSurvey(String userId);

}
