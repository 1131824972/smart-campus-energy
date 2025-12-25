package com.wyczzz.smartcampusenergy.service.impl;

import com.wyczzz.smartcampusenergy.entity.Building;
import com.wyczzz.smartcampusenergy.repository.BuildingRepository;
import com.wyczzz.smartcampusenergy.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 重点1：标记这是业务逻辑层的 Bean
public class BuildingServiceImpl implements BuildingService {

    @Autowired // 重点2：自动注入 Repository，不需要 new
    private BuildingRepository buildingRepository;

    @Override
    @Transactional // 重点3：开启事务。如果保存10个建筑，第5个失败了，前面的会自动回滚
    public void initData(List<Building> buildings) {
        // 简单粗暴：直接调用 JPA 的 saveAll
        buildingRepository.saveAll(buildings);
    }

    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Building findById(Long id) {
        // orElse(null) 表示如果没查到就返回 null，防止报错
        return buildingRepository.findById(id).orElse(null);
    }
}