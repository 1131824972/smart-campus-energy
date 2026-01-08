package com.wyczzz.smartcampusenergy.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.wyczzz.smartcampusenergy.entity.Device;
import com.wyczzz.smartcampusenergy.entity.EnergyData;
import com.wyczzz.smartcampusenergy.pattern.factory.PowerGenStrategyFactory;
import com.wyczzz.smartcampusenergy.pattern.observer.EnergyDataGeneratedEvent;
import com.wyczzz.smartcampusenergy.pattern.strategy.PowerGenStrategy;
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

    // 【改动】不再直接注入 List<Strategy>，而是注入工厂
    @Autowired
    private PowerGenStrategyFactory strategyFactory;

    // 内存缓存：记录每个设备当前的累计用电量
    private final Map<Long, Double> kwhCounter = new ConcurrentHashMap<>();

    // 定时任务：每 5000 毫秒执行一次
    @Scheduled(fixedRate = 5000)
    public void simulateAllDevices() {
        List<Device> devices = deviceService.findAll();
        if (devices.isEmpty()) return;

        Date now = new Date();
        int currentHour = DateUtil.hour(now, true);

        // 随机判定当前是否发生异常 (5% 概率)
        boolean isAnomaly = RandomUtil.randomInt(0, 100) < 5;

        // 【改动】通过工厂模式获取策略，代码语义更清晰
        PowerGenStrategy currentStrategy = strategyFactory.getStrategy(currentHour, isAnomaly);

        System.out.println("⚡ [模拟器] 时间:" + currentHour + "点 | 异常:" + isAnomaly
                + " | 策略:" + currentStrategy.getClass().getSimpleName());

        for (Device device : devices) {
            // 1. 策略模式：生成数据
            double[] result = currentStrategy.generate(device);
            double voltage = result[0];
            double power = result[1];

            // 2. 业务计算
            double incrementKwh = (power * 5) / 3600000.0;
            double currentTotalKwh = kwhCounter.getOrDefault(device.getId(), 0.0) + incrementKwh;
            kwhCounter.put(device.getId(), currentTotalKwh);

            EnergyData data = new EnergyData();
            data.setDevice(device);
            data.setVoltage(voltage);
            data.setPower(power);
            data.setCurrentVal(power / voltage);
            data.setKwh(currentTotalKwh);

            energyDataRepository.save(data);

            // 3. 观察者模式：发布事件 (现在是异步处理了！)
            eventPublisher.publishEvent(new EnergyDataGeneratedEvent(this, data));
        }
    }
}