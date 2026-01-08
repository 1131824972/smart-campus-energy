package com.wyczzz.smartcampusenergy.pattern.factory;

import com.wyczzz.smartcampusenergy.pattern.strategy.PowerGenStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 【设计模式】简单工厂模式 (Simple Factory)
 * 职责：负责管理所有的策略实例，并根据环境上下文(Context)提供最合适的策略
 */
@Component
public class PowerGenStrategyFactory {

    @Autowired
    private List<PowerGenStrategy> strategies;

    /**
     * 根据当前时间和异常状态，生产/查找一个匹配的策略
     */
    public PowerGenStrategy getStrategy(int currentHour, boolean isAnomaly) {
        // 遍历所有策略，找到第一个匹配的
        return strategies.stream()
                .filter(s -> s.matches(currentHour, isAnomaly))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("工厂异常：无法找到匹配的能耗生成策略"));
    }
}