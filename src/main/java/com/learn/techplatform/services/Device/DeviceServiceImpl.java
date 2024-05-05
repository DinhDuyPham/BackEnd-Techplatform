package com.learn.techplatform.services.Device;

import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.utils.AppUtil;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.controllers.models.request.DeviceRequest;
import com.learn.techplatform.entities.Device;
import com.learn.techplatform.repositories.DeviceRepository;
import com.learn.techplatform.services.AbstractBaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl extends AbstractBaseService<Device, String> implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public DeviceServiceImpl(JpaRepository<Device, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public void saveDevice(DeviceRequest deviceRequest, String userId, HttpServletRequest request) {
        String ipAddress = AppUtil.getClientIpAddress(request);
        Device device = this.deviceRepository.findByFcmTokenAndUserIdAndSystemStatus(deviceRequest.getFcmToken(),userId, SystemStatus.ACTIVE);
        if (device == null) {
            device = Device.builder()
                    .userId(userId)
                    .fcmToken(deviceRequest.getFcmToken())
                    .id(UniqueID.getUUID())
                    .systemStatus(SystemStatus.ACTIVE)
                    .build();
        }
        device.setIpAddress(ipAddress);
        device.setBrowser(request.getHeader(Constant.UserAgent));
        this.save(device);
    }

    @Override
    public List<Device> getAllDevice(String userId) {
        return deviceRepository.findByUserIdAndSystemStatus(userId, SystemStatus.ACTIVE);
    }
}
