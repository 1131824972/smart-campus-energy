package com.wyczzz.smartcampusenergy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class AlarmRecordDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; // 告警时间

    private String type;     // 告警类型
    private String details;  // 详情
    private Double value;    // 异常数值

    // 关联设备的信息
    private String deviceName;
    private String deviceSn;
}