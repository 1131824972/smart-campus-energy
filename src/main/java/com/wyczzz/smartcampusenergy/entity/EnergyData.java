package com.wyczzz.smartcampusenergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*; // 确保是 jakarta
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "energy_data")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EnergyData extends BaseEntity { // 继承 BaseEntity 自动获得 id 和 createTime

    // 外键：所属设备
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    @JsonIgnoreProperties({"building", "energyDatas"}) // 防止序列化死循环
    private Device device;

    private Double voltage;      // 电压 (V)
    private Double currentVal;   // 电流 (A)
    private Double power;        // 功率 (W)

    @Column(name = "kwh")
    private Double kwh;          // 累计用电量 (kWh)
}