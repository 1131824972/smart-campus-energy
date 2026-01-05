package com.wyczzz.smartcampusenergy.repository;

import com.wyczzz.smartcampusenergy.entity.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long>, JpaSpecificationExecutor<EnergyData> {

    // Top10: 取前10条
    // OrderByCreateTimeDesc: 按时间倒序
    List<EnergyData> findTop10ByDeviceIdOrderByCreateTimeDesc(Long deviceId);
}