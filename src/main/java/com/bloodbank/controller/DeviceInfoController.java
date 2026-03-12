package com.bloodbank.controller;

import com.bloodbank.entity.DeviceInfo;
import com.bloodbank.service.DeviceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceInfoController {

    private final DeviceInfoService deviceInfoService;

    @GetMapping
    public ResponseEntity<List<DeviceInfo>> getAll() {
        return ResponseEntity.ok(deviceInfoService.getAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<DeviceInfo>> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(deviceInfoService.getByUsername(username));
    }
}

