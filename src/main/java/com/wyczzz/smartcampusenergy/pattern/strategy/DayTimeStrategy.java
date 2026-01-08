package com.wyczzz.smartcampusenergy.pattern.strategy;

import cn.hutool.core.util.RandomUtil;
import com.wyczzz.smartcampusenergy.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DayTimeStrategy implements PowerGenStrategy {

    @Override
    public boolean matches(int currentHour, boolean isAnomaly) {
        // 条件：非异常状态 且 时间在 8点到22点之间 (含8点，不含22点)
        return !isAnomaly && (currentHour >= 8 && currentHour < 22);
    }

    @Override
    public double[] generate(Device device) {
        double voltage = RandomUtil.randomDouble(210.0, 235.0);
        double maxPower = device.getMaxPower();
        double power = RandomUtil.randomDouble(maxPower * 0.2, maxPower * 0.9);
        return new double[]{voltage, power};
    }
}