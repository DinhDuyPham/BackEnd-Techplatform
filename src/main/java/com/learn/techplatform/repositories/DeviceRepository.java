package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    Device findByFcmTokenAndUserIdAndSystemStatus(String fcmToken, String userId, SystemStatus systemStatus);

    List<Device> findByUserIdAndSystemStatus(String userId, SystemStatus systemStatus);
}
