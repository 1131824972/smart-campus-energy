package com.wyczzz.smartcampusenergy.pattern.strategy;

import com.wyczzz.smartcampusenergy.entity.Device;

/**
 * 【设计模式】策略模式接口 - 重构版
 * 增加了 matches 方法，实现策略的“自判断”，消除 Service 层的 if-else
 */
public interface PowerGenStrategy {

    /**
     * 核心算法：生成电压和功率
     */
    double[] generate(Device device);

    /**
     * 策略匹配器：判断当前环境是否适用该策略
     * @param currentHour 当前小时数 (0-23)
     * @param isAnomaly  是否触发了随机异常标记
     * @return true=适用, false=不适用
     */
    boolean matches(int currentHour, boolean isAnomaly);
}