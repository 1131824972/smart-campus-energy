package com.wyczzz.smartcampusenergy.repository;

import com.wyczzz.smartcampusenergy.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {

    // 用于检查建筑名称是否重复
    List<Building> findByName(String name);
}