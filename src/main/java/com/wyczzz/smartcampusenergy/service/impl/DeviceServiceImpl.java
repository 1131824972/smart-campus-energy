package com.wyczzz.smartcampusenergy.service.impl;

import com.wyczzz.smartcampusenergy.entity.Device;
import com.wyczzz.smartcampusenergy.repository.DeviceRepository;
import com.wyczzz.smartcampusenergy.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    @Transactional
    public void initData(List<Device> devices) {
       deviceRepository.saveAll(devices);
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
}