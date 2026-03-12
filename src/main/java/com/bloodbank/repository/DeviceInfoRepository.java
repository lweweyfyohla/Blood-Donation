package com.bloodbank.repository;

import com.bloodbank.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {
    List<DeviceInfo> findByUsernameOrderByLoginTimeDesc(String username);
    List<DeviceInfo> findAllByOrderByLoginTimeDesc();
}

