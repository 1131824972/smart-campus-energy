package com.wyczzz.smartcampusenergy.service;

import com.wyczzz.smartcampusenergy.entity.Device;
import java.util.List;

public interface DeviceService {
    // 初始化数据用
    void initData(List<Device> devices);

    // 基础查询
    List<Device> findAll();
}