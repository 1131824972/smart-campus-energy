package com.wyczzz.smartcampusenergy.pattern.strategy;

import cn.hutool.core.util.RandomUtil;
import com.wyczzz.smartcampusenergy.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class NightModeStrategy implements PowerGenStrategy {

    @Override
    public boolean matches(int currentHour, boolean isAnomaly) {
        // 条件：非异常状态 且 (早于8点 或 晚于等于22点)
        return !isAnomaly && (currentHour < 8 || currentHour >= 22);
    }

    @Override
    public double[] generate(Device device) {
        double voltage = RandomUtil.randomDouble(215.0, 225.0);
        // 夜间待机功耗 10W-100W
        double power = RandomUtil.randomDouble(10.0, 100.0);
        return new double[]{voltage, power};
    }
}