package com.learn.techplatform.common.exceptions;

import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;

public class ApplicationException  extends RuntimeException {
    private RestAPIStatus apiStatus;
    private Object data;

    public ApplicationException(RestAPIStatus apiStatus) {
        super(apiStatus.getDescription());
        this.apiStatus = apiStatus;
    }

    /**
     * This constructor is builded only for handling BAD REQUEST exception
     * Careful when use it with other purpose ;)
     *
     * @param apiStatus
     * @param data
     */
    public ApplicationException(RestAPIStatus apiStatus, Object data) {
        this(apiStatus);
        this.data = data;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(RestAPIStatus apiStatus, String message) {
        super(message);
        this.apiStatus = apiStatus;
    }

    public ApplicationException(RestAPIStatus apiStatus, RestStatusMessage message) {
        super(message.toString());
        this.apiStatus = apiStatus;
    }

    public RestAPIStatus getApiStatus() {
        return apiStatus;
    }

    public Object getData() {
        return data;
    }
}
