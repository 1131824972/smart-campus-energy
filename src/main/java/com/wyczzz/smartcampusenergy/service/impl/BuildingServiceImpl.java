package com.wyczzz.smartcampusenergy.service.impl;

import com.wyczzz.smartcampusenergy.entity.Building;
import com.wyczzz.smartcampusenergy.repository.BuildingRepository;
import com.wyczzz.smartcampusenergy.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    @Transactional // 开启事务。如果保存10个建筑，第5个失败了，前面的会自动回滚
    public void initData(List<Building> buildings) {
        buildingRepository.saveAll(buildings);
    }

    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Building findById(Long id) {
        return buildingRepository.findById(id).orElse(null);
    }
}