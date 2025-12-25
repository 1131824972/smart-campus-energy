package com.wyczzz.smartcampusenergy.pattern.observer;

import com.wyczzz.smartcampusenergy.entity.EnergyData;
import org.springframework.context.ApplicationEvent;

/**
 * 【设计模式】观察者模式 - 事件对象
 * 当模拟器生成一条能耗数据后，会发布这个事件
 */
public class EnergyDataGeneratedEvent extends ApplicationEvent {

    private final EnergyData energyData;

    public EnergyDataGeneratedEvent(Object source, EnergyData energyData) {
        super(source);
        this.energyData = energyData;
    }

    public EnergyData getEnergyData() {
        return energyData;
    }
}