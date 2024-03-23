package com.learn.techplatform.services.User;

import com.learn.techplatform.dto_modals.UserDTO;
import com.learn.techplatform.entities.User;
import com.learn.techplatform.services.InterfaceBaseService;

public interface UserService extends InterfaceBaseService<User, String> {

    UserDTO getUserByEmail(String email);
    UserDTO getAuthInfo(String id);

}
