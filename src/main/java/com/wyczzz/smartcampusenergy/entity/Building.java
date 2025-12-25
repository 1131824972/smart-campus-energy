package com.wyczzz.smartcampusenergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
        import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity // 标记这是一个数据库实体
@Table(name = "building") // 对应数据库表名
@Where(clause = "is_deleted=0") // 重点4：全局过滤，查询时自动加上 where is_deleted=0，实现真正的软删除效果
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Building extends LogicEntity {

    @Column(nullable = false)
    private String name; // 建筑名称 (如：力行楼)

    private String location; // 位置编号 (如：Zone-A)

    private Integer floors; // 楼层数

    private String purpose; // 建筑用途 (如：教学、宿舍、实验)

    // 关系：一栋建筑包含多个设备
    // mappedBy = "building" 表示关系的维护端在 Device 类的 building 属性那边
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("building") // 重点5：防止死循环！序列化 Building 时，不要去序列化 Device 里的 building 字段
    private List<Device> devices = new ArrayList<>();
}