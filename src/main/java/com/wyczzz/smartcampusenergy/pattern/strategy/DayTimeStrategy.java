package com.wyczzz.smartcampusenergy.pattern.strategy;

import cn.hutool.core.util.RandomUtil; // 使用 Hutool 工具包生成随机数
import com.wyczzz.smartcampusenergy.entity.Device;
import org.springframework.stereotype.Component;

@Component // 注册为 Bean，方便调用
public class DayTimeStrategy implements PowerGenStrategy {

    @Override
    public double[] generate(Device device) {
        // 1. 模拟电压：标准民用电压 220V，波动范围 210V - 235V
        // RandomUtil.randomDouble(min, max)
        double voltage = RandomUtil.randomDouble(210.0, 235.0);

        // 2. 模拟功率：日间活跃模式
        // 功率范围：额定功率的 20% - 90%
        double maxPower = device.getMaxPower();
        double power = RandomUtil.randomDouble(maxPower * 0.2, maxPower * 0.9);

        return new double[]{voltage, power};
    }
}