package com.wyczzz.smartcampusenergy.repository;

import com.wyczzz.smartcampusenergy.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 标记这是一个仓库 Bean
public interface BuildingRepository extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {

    // 🔨 自动生成 SQL：select * from building where name = ?
    // 用于检查建筑名称是否重复
    List<Building> findByName(String name);
}