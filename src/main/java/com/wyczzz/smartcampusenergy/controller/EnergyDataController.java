package com.wyczzz.smartcampusenergy.controller;

import com.wyczzz.smartcampusenergy.dto.EnergyDataDTO;
import com.wyczzz.smartcampusenergy.entity.EnergyData;
import com.wyczzz.smartcampusenergy.repository.EnergyDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "能耗数据监控接口")
@RestController
@RequestMapping("/api/energy-data")
public class EnergyDataController {

    @Autowired
    private EnergyDataRepository energyDataRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "获取某设备最新的10条能耗数据(用于实时折线图)")
    @GetMapping("/recent")
    public List<EnergyDataDTO> getRecentData(
            @Parameter(description = "设备ID") @RequestParam Long deviceId) {

        // 1. 调用 Repository 查询最新的 10 条数据
        List<EnergyData> list = energyDataRepository.findTop10ByDeviceIdOrderByCreateTimeDesc(deviceId);

        // 2. 注意：查出来是倒序的（最新的在前面），但画折线图通常要按时间正序（旧 -> 新）
        // 所以我们手动翻转一下列表
        Collections.reverse(list);

        // 3. 转 DTO
        return list.stream()
                .map(data -> modelMapper.map(data, EnergyDataDTO.class))
                .collect(Collectors.toList());
    }
}