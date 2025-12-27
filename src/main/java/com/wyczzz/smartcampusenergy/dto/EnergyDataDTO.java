package com.wyczzz.smartcampusenergy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class EnergyDataDTO {
    private Long id;

    // 格式化时间，方便前端直接展示
    // pattern = "HH:mm:ss" 表示只显示 时:分:秒 (折线图横轴通常只需要时间)
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Double voltage;    // 电压
    private Double power;      // 功率
    private Double kwh;        // 累计用电
}