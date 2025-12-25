package com.wyczzz.smartcampusenergy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass // 重点1：表示这是一个父类，不会映射成单独的表，但它的属性会被子类继承到各自的表中
@EntityListeners(AuditingEntityListener.class) // 重点2：开启JPA审计监听，自动填充创建/修改时间
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 重点3：这是我们在实验5学到的“防爆盾”，防止Jackson序列化报错
public abstract class BaseEntity implements Serializable {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增策略
    protected Long id;

    @CreatedDate // 插入时自动填入当前时间
    @Column(nullable = false, updatable = false) // 不允许为空，且初始化后不可更改
    protected Date createTime;

    @LastModifiedDate // 每次更新时自动填入当前时间
    @Column
    protected Date updateTime;
}