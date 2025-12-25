package com.wyczzz.smartcampusenergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "device")
@Where(clause = "is_deleted=0")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Device extends LogicEntity {

    @Column(nullable = false)
    private String name; // 设备名称 (如：宿舍智能电表-01)

    // 重点6：unique = true 保证SN号在数据库层面唯一，符合项目约束
    @Column(nullable = false, unique = true)
    private String sn; // 唯一设备序列号 (如：METER_2025001)

    private String status; // 通讯状态 (ONLINE, OFFLINE)

    @Column(name = "max_power")
    private Double maxPower; // 额定功率阈值 (如：1000.0)

    @Column(name = "room_number")
    private String roomNumber; // 所属房间号 (如：301)

    // 关系：多个设备属于一栋建筑
    @ManyToOne(fetch = FetchType.LAZY) // 懒加载，提高性能
    @JoinColumn(name = "building_id", nullable = false) // 外键列名
    @JsonIgnoreProperties("devices") // 重点5：防止死循环！序列化 Device 时，不要去序列化 Building 里的 devices 列表
    private Building building;
}