package com.learn.techplatform.services.User;

import com.learn.techplatform.common.enums.GenderType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.enums.UserStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.DateUtil;
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
    public void editUserInfo(String id, EditUserRequest editUserRequest) {
        User user = userRepository.findByIdAndSystemStatusAndUserStatus(id, SystemStatus.ACTIVE, UserStatus.ACTIVE);
        Validator.notNull(user, RestAPIStatus.NOT_FOUND, RestStatusMessage.USER_NOT_FOUND);

        if (editUserRequest.getFirstName() != null) {
            boolean isFirstnameValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getFirstName());
            Validator.mustTrue(isFirstnameValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_FIRSTNAME_FORMAT);
            user.setFirstName(editUserRequest.getFirstName());
        }

        if (editUserRequest.getLastName() != null) {
            boolean isLastnameValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getLastName());
            Validator.mustTrue(isLastnameValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_LASTNAME_FORMAT);
            user.setLastName(editUserRequest.getLastName());
        }

        if (editUserRequest.getPhoneNumber() != null) {
            boolean isPhoneNumberValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getPhoneNumber());
            Validator.mustTrue(isPhoneNumberValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_PHONE_NUMBER_FORMAT);
            Validator.validatePhoneNumber(editUserRequest.getPhoneNumber());
            user.setPhoneNumber(editUserRequest.getPhoneNumber());
        }

        if (editUserRequest.getDateOfBirth() != null) {
            boolean isDateOfBirthValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getDateOfBirth());
            Validator.mustTrue(isDateOfBirthValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DATE_FORMAT);
            Long formatDate = DateUtil.convertStringDateToLong(editUserRequest.getDateOfBirth());
            user.setDateOfBirth(formatDate);
        }

        if (Validator.checkNull(editUserRequest.getGender()))
            user.setGender(null);
        else {
            boolean isNotGenderValid = Validator.checkEmptyString(editUserRequest.getGender());
            Validator.mustTrue(!isNotGenderValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_GENDER_FORMAT);
            user.setGender(GenderType.valueOf(editUserRequest.getGender().toUpperCase()));
        }

        if (editUserRequest.getBio() != null) {
            boolean isBioValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getBio());
            Validator.mustTrue(isBioValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_BIO_FORMAT);
            user.setBio(editUserRequest.getBio());
        }

        if (editUserRequest.getProfileImage() != null) {
            boolean isProfileImageValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getProfileImage());
            Validator.mustTrue(isProfileImageValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_UPLOAD_IMAGE);
            user.setProfileImage(editUserRequest.getProfileImage());
        }

        if (editUserRequest.getCoverImage() != null) {
            boolean isCoverImageValid = Validator.checkNotNullAndNotEmptyString(editUserRequest.getCoverImage());
            Validator.mustTrue(isCoverImageValid, RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_UPLOAD_IMAGE);
            user.setCoverImage(editUserRequest.getCoverImage());
        }

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
