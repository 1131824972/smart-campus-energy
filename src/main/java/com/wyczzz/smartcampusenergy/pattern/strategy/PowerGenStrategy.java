package com.wyczzz.smartcampusenergy.pattern.strategy;

import com.wyczzz.smartcampusenergy.entity.Device;

/**
 * 【设计模式】策略模式接口
 * 定义生成能耗数据的算法标准
 * 不同的实现类将对应不同的模拟场景（白天、夜间、异常突刺）
 */
public interface PowerGenStrategy {

    /**
     * 根据设备的最大功率，计算当前的模拟功率和电压
     * @param device 设备信息
     * @return double[] 数组，index[0]=电压, index[1]=功率
     */
    double[] generate(Device device);
}