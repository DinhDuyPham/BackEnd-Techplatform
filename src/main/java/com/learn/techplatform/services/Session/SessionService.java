package com.learn.techplatform.services.Session;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.Session;
import com.learn.techplatform.services.InterfaceBaseService;

public interface SessionService extends InterfaceBaseService<Session, String> {

    Session getByIdAndSystemStatus(String id, SystemStatus status);

    Session getByDataAndSystemStatus(String data, SystemStatus status);
}
