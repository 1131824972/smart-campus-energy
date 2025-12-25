package com.wyczzz.smartcampusenergy.pattern.observer;

import com.wyczzz.smartcampusenergy.entity.AlarmRecord;
import com.wyczzz.smartcampusenergy.entity.Device;
import com.wyczzz.smartcampusenergy.entity.EnergyData;
import com.wyczzz.smartcampusenergy.repository.AlarmRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 【设计模式】观察者模式 - 具体观察者
 * 监听 EnergyDataGeneratedEvent 事件，负责异常检测和告警入库
 */
@Component
public class AlarmEventListener {

    @Autowired
    private AlarmRecordRepository alarmRecordRepository;

    // @EventListener 注解标识这是一个观察者方法
    // @Async 表示异步执行（可选），防止告警逻辑卡住数据生成的主线程
    @EventListener
    public void handleEnergyDataEvent(EnergyDataGeneratedEvent event) {
        EnergyData data = event.getEnergyData();
        Device device = data.getDevice();

        // 1. 检查电压是否异常
        // 标准 220V，波动超过 10% (即 < 198V 或 > 242V) 视为异常
        if (data.getVoltage() < 198.0 || data.getVoltage() > 242.0) {
            saveAlarm(device, "电压异常", "检测到电压不稳定: " + data.getVoltage() + "V", data.getVoltage());
        }

        // 2. 检查功率是否过载
        // 超过设备额定功率即为过载
        if (data.getPower() > device.getMaxPower()) {
            saveAlarm(device, "功率过载", "当前功率(" + data.getPower() + "W) 超过额定值 (" + device.getMaxPower() + "W)", data.getPower());
        }
    }

    private void saveAlarm(Device device, String type, String details, Double val) {
        AlarmRecord alarm = new AlarmRecord();
        alarm.setDevice(device);
        alarm.setType(type);
        alarm.setDetails(details);
        alarm.setValue(val);

        alarmRecordRepository.save(alarm);
        System.out.println("⚠️ [告警触发] 设备: " + device.getName() + " | 原因: " + details);
    }
}