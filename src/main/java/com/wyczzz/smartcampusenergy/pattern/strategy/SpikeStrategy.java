package com.wyczzz.smartcampusenergy.pattern.strategy;

import cn.hutool.core.util.RandomUtil;
import com.wyczzz.smartcampusenergy.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class SpikeStrategy implements PowerGenStrategy {

    @Override
    public double[] generate(Device device) {
        // 随机决定是搞“电压异常”还是“功率过载”
        boolean isVoltageIssue = RandomUtil.randomBoolean();

        double voltage;
        double power;

        if (isVoltageIssue) {
            // 类型B：电压不稳 (突然跌到180V或者飙升到260V)
            voltage = RandomUtil.randomBoolean() ? 180.0 : 260.0;
            // 功率正常一点
            power = RandomUtil.randomDouble(100.0, 500.0);
        } else {
            // 类型A：功率过载 (飙升到 1.2 倍额定功率)
            voltage = 220.0;
            double maxPower = device.getMaxPower();
            power = maxPower * 1.2;
        }

        return new double[]{voltage, power};
    }
}