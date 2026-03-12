package com.bloodbank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "device_type", length = 20)
    private String deviceType;

    @Column(length = 50)
    private String browser;

    @Column(name = "operating_system", length = 50)
    private String operatingSystem;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @CreationTimestamp
    @Column(name = "login_time", nullable = false, updatable = false)
    private LocalDateTime loginTime;
}

