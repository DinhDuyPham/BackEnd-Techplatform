package com.learn.techplatform.controllers;

import com.learn.techplatform.common.exceptions.ApplicationException;
import com.learn.techplatform.common.restfullApi.ResponseUtil;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.AppValueConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractBaseController {
    @Autowired
    ResponseUtil responseUtil;
    @Autowired
    AppValueConfigure appValueConfigure;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void validatePaging(int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new ApplicationException(RestAPIStatus.BAD_PARAMS, RestStatusMessage.INVALID_PAGING_REQUEST);
        }
    }
}
