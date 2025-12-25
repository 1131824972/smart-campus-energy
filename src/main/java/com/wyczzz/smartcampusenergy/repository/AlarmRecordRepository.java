package com.wyczzz.smartcampusenergy.repository;

import com.wyczzz.smartcampusenergy.entity.AlarmRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRecordRepository extends JpaRepository<AlarmRecord, Long>, JpaSpecificationExecutor<AlarmRecord> {
    // 暂时用默认方法即可，需要复杂查询时再加
}