package com.wyczzz.smartcampusenergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class LogicEntity extends BaseEntity {

    // 逻辑删除标记：0=未删，1=已删（默认0）
    // insertable = false 表示插入时忽略该字段（使用数据库默认值0）
    @Column(insertable = false, columnDefinition = "int default 0")
    protected Integer isDeleted = 0; // 初始化为0，防止空指针

    // 删除时间
    @Column
    protected Date deletedTime;
}