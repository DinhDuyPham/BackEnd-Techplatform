package com.learn.techplatform.services.Session;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.Session;
import com.learn.techplatform.repositories.SessionRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl  extends AbstractBaseService<Session, String> implements SessionService {
    @Autowired
    SessionRepository sessionRepository;

    public SessionServiceImpl(JpaRepository<Session, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public Session getByIdAndSystemStatus(String id, SystemStatus status) {
        return sessionRepository.getByIdAndSystemStatus(id, status);
    }

    @Override
    public Session getByDataAndSystemStatus(String data, SystemStatus status) {
        return sessionRepository.getByDataAndSystemStatus(data, status);
    }
}
