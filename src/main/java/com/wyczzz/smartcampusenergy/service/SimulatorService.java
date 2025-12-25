package com.wyczzz.smartcampusenergy.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.wyczzz.smartcampusenergy.entity.Device;
import com.wyczzz.smartcampusenergy.entity.EnergyData;
import com.wyczzz.smartcampusenergy.pattern.observer.EnergyDataGeneratedEvent;
import com.wyczzz.smartcampusenergy.pattern.strategy.DayTimeStrategy;
import com.wyczzz.smartcampusenergy.pattern.strategy.NightModeStrategy;
import com.wyczzz.smartcampusenergy.pattern.strategy.PowerGenStrategy;
import com.wyczzz.smartcampusenergy.pattern.strategy.SpikeStrategy;
import com.wyczzz.smartcampusenergy.repository.EnergyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimulatorService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EnergyDataRepository energyDataRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private DayTimeStrategy dayTimeStrategy;
    @Autowired
    private NightModeStrategy nightModeStrategy;
    @Autowired
    private SpikeStrategy spikeStrategy;

    // 内存缓存：记录每个设备当前的累计用电量
    private final Map<Long, Double> kwhCounter = new ConcurrentHashMap<>();

    // 定时任务：每 5000 毫秒执行一次
    @Scheduled(fixedRate = 5000)
    public void simulateAllDevices() {
        List<Device> devices = deviceService.findAll();
        if (devices.isEmpty()) return;

        Date now = new Date();
        int currentHour = DateUtil.hour(now, true);

        System.out.println("⚡ [模拟器] 开始生成 " + devices.size() + " 个设备的数据...");

        for (Device device : devices) {
            // 1. 选择策略
            PowerGenStrategy strategy = selectStrategy(currentHour);

            // 2. 生成数据
            double[] result = strategy.generate(device);
            double voltage = result[0];
            double power = result[1];

            // 3. 计算累计用电量
            double incrementKwh = (power * 5) / 3600000.0;
            double currentTotalKwh = kwhCounter.getOrDefault(device.getId(), 0.0) + incrementKwh;
            kwhCounter.put(device.getId(), currentTotalKwh);

            // 4. 封装对象
            EnergyData data = new EnergyData();
            data.setDevice(device);
            data.setVoltage(voltage);
            data.setPower(power);
            data.setCurrentVal(power / voltage);
            data.setKwh(currentTotalKwh);

            // 5. 存库
            energyDataRepository.save(data);

            // 6. 发布事件 (观察者模式)
            eventPublisher.publishEvent(new EnergyDataGeneratedEvent(this, data));
        }
    }

    private PowerGenStrategy selectStrategy(int hour) {
        // 5% 概率发生故障
        if (RandomUtil.randomInt(0, 100) < 5) {
            return spikeStrategy;
        }

        // 日间 模式: 08:00 - 22:00
        if (hour >= 8 && hour < 22) {
            return dayTimeStrategy;
        } else {
            // 夜间 模式
            return nightModeStrategy;
        }
    }
}