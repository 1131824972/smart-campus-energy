package com.wyczzz.smartcampusenergy.pattern.strategy;

import cn.hutool.core.util.RandomUtil;
import com.wyczzz.smartcampusenergy.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class NightModeStrategy implements PowerGenStrategy {

    @Override
    public double[] generate(Device device) {
        // 1. 电压依旧正常波动
        double voltage = RandomUtil.randomDouble(215.0, 225.0); // 夜间电压可能更稳一点

        // 2. 模拟功率：夜间休眠模式
        // 功率范围：10W - 100W (待机功耗)
        double power = RandomUtil.randomDouble(10.0, 100.0);

        return new double[]{voltage, power};
    }
}