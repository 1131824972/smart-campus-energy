package com.wyczzz.smartcampusenergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "alarm_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AlarmRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    @JsonIgnoreProperties("building")
    private Device device;

    private String type;         // 告警类型 (如：过载、电压异常)
    private String details;      // 详情描述
    private Double value;        // 触发告警时的数值
}