package com.wyczzz.smartcampusenergy.controller;

import com.wyczzz.smartcampusenergy.dto.DeviceDTO;
import com.wyczzz.smartcampusenergy.entity.Device;
import com.wyczzz.smartcampusenergy.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "设备管理接口")
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "获取所有设备列表")
    @GetMapping
    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceService.findAll();

        // Entity -> DTO
        return devices.stream()
                .map(device -> {
                    // map() 方法会自动匹配同名字段 (如 name, sn)
                    DeviceDTO dto = modelMapper.map(device, DeviceDTO.class);
                    // 手动补充一些特殊字段（如果自动匹配没成功的话）
                    // 比如 Entity 里是 building 对象，但 DTO 里我们想要 buildingName
                    if (device.getBuilding() != null) {
                        dto.setBuildingId(device.getBuilding().getId());
                        dto.setBuildingName(device.getBuilding().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}