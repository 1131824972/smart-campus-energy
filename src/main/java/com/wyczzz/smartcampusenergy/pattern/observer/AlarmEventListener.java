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

    /**
     * @Async: 开启异步执行。
     * 即使数据库写入变慢，也不会阻塞 SimulatorService 的数据生成主线程。
     * 这体现了观察者模式在“解耦”方面的巨大优势。
     */
    @Async
    @EventListener
    public void handleEnergyDataEvent(EnergyDataGeneratedEvent event) {
        EnergyData data = event.getEnergyData();
        Device device = data.getDevice();

        // 1. 检查电压是否异常 ( < 198V 或 > 242V )
        if (data.getVoltage() < 198.0 || data.getVoltage() > 242.0) {
            saveAlarm(device, "电压异常", "检测到电压不稳定: " + String.format("%.2f", data.getVoltage()) + "V", data.getVoltage());
        }

        // 2. 检查功率是否过载
        if (data.getPower() > device.getMaxPower()) {
            saveAlarm(device, "功率过载", "当前功率(" + String.format("%.2f", data.getPower()) + "W) 超过额定值", data.getPower());
        }
    }

    private void saveAlarm(Device device, String type, String details, Double val) {
        AlarmRecord alarm = new AlarmRecord();
        alarm.setDevice(device);
        alarm.setType(type);
        alarm.setDetails(details);
        alarm.setValue(val);

        alarmRecordRepository.save(alarm);
        System.out.println("⚠️ [异步告警] 线程:" + Thread.currentThread().getName() + " | 设备:" + device.getName() + " | " + details);
    }
}