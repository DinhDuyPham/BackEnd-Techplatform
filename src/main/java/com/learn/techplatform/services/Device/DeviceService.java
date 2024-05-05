package com.learn.techplatform.services.Device;

import com.learn.techplatform.controllers.models.request.DeviceRequest;
import com.learn.techplatform.entities.Device;
import com.learn.techplatform.services.InterfaceBaseService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DeviceService extends InterfaceBaseService<Device, String> {

    void saveDevice(DeviceRequest deviceRequest, String userId, HttpServletRequest request);
    List<Device> getAllDevice(String userId);
}
