package com.wyczzz.smartcampusenergy.service;

import com.wyczzz.smartcampusenergy.entity.Building;
import java.util.List;

public interface BuildingService {
    // 初始化数据用：批量保存
    void initData(List<Building> buildings);

    // 查询所有建筑
    List<Building> findAll();

    // 根据ID查
    Building findById(Long id);
}