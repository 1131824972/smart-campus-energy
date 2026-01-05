package com.wyczzz.smartcampusenergy.repository;

import com.wyczzz.smartcampusenergy.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    // 用于根据序列号(SN)查找设备，Optional 表示可能查不到
    Optional<Device> findBySn(String sn);

    // 用于新建设备时校验 SN 是否已存在
    boolean existsBySn(String sn);

    // 用于校验：同一个房间是否已经绑定了设备
    Optional<Device> findByBuildingIdAndRoomNumber(Long buildingId, String roomNumber);
}