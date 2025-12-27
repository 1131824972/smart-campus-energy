package com.wyczzz.smartcampusenergy.controller;

import com.wyczzz.smartcampusenergy.dto.AlarmRecordDTO;
import com.wyczzz.smartcampusenergy.entity.AlarmRecord;
import com.wyczzz.smartcampusenergy.repository.AlarmRecordRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "告警管理接口")
@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    @Autowired
    private AlarmRecordRepository alarmRecordRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "获取所有历史告警记录")
    @GetMapping
    public List<AlarmRecordDTO> getAllAlarms() {
        // 按时间倒序查所有（最新的告警排在最上面）
        List<AlarmRecord> alarms = alarmRecordRepository.findAll(
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return alarms.stream()
                .map(alarm -> {
                    AlarmRecordDTO dto = modelMapper.map(alarm, AlarmRecordDTO.class);
                    // 手动设置设备信息（如果 ModelMapper 没自动映射到的话）
                    if (alarm.getDevice() != null) {
                        dto.setDeviceName(alarm.getDevice().getName());
                        dto.setDeviceSn(alarm.getDevice().getSn());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}