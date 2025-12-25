package com.wyczzz.smartcampusenergy.controller;

import com.wyczzz.smartcampusenergy.dto.BuildingDTO;
import com.wyczzz.smartcampusenergy.entity.Building;
import com.wyczzz.smartcampusenergy.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "建筑管理接口") // Swagger 文档分组名称
@RestController // 标记为返回 JSON 的控制器
@RequestMapping("/api/buildings") // 统一路由前缀
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "获取所有建筑列表") // 接口描述
    @GetMapping
    public List<BuildingDTO> getAllBuildings() {
        // 1. Service 层获取实体列表
        List<Building> buildings = buildingService.findAll();

        // 2. 转换：Entity -> DTO
        return buildings.stream()
                .map(building -> modelMapper.map(building, BuildingDTO.class))
                .collect(Collectors.toList());
    }
}