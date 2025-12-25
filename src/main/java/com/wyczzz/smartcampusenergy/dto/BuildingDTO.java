package com.wyczzz.smartcampusenergy.dto;

import lombok.Data;

@Data // Lombok 自动生成 Getter/Setter
public class BuildingDTO {
    private Long id;
    private String name;     // 建筑名称
    private String location; // 位置
    private String purpose;  // 用途
}