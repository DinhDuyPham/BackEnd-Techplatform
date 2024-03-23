package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.Session;
import com.learn.techplatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    Session getByIdAndSystemStatus(String id, SystemStatus systemStatus);

    Session getByDataAndSystemStatus(String data, SystemStatus status);

}
