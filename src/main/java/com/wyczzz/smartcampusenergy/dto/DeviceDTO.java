package com.wyczzz.smartcampusenergy.dto;

import lombok.Data;

@Data
public class DeviceDTO {
    private Long id;
    private String name;       // 设备名
    private String sn;         // 序列号
    private String status;     // 状态
    private String roomNumber; // 房间
    private Double maxPower;   // 额定功率

    // 扁平化展示：我们不放整个 Building 对象，只放建筑的名字和ID
    // 这样前端展示表格时更方便，不用写 device.building.name
    private Long buildingId;
    private String buildingName;
}