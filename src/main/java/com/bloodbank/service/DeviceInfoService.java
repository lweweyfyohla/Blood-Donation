package com.bloodbank.service;

import com.bloodbank.entity.DeviceInfo;
import com.bloodbank.repository.DeviceInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceInfoService {

    private final DeviceInfoRepository deviceInfoRepository;

    @Transactional
    public DeviceInfo recordDevice(String username, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ip = getClientIp(request);

        DeviceInfo info = DeviceInfo.builder()
                .username(username)
                .ipAddress(ip)
                .deviceType(parseDeviceType(userAgent))
                .browser(parseBrowser(userAgent))
                .operatingSystem(parseOS(userAgent))
                .userAgent(userAgent)
                .build();

        return deviceInfoRepository.save(info);
    }

    @Transactional(readOnly = true)
    public List<DeviceInfo> getByUsername(String username) {
        return deviceInfoRepository.findByUsernameOrderByLoginTimeDesc(username);
    }

    @Transactional(readOnly = true)
    public List<DeviceInfo> getAll() {
        return deviceInfoRepository.findAllByOrderByLoginTimeDesc();
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty()) {
            return xff.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }
        return request.getRemoteAddr();
    }

    private String parseOS(String ua) {
        if (ua == null) return "Unknown";
        if (ua.contains("Windows NT 10")) return "Windows 10/11";
        if (ua.contains("Windows")) return "Windows";
        if (ua.contains("Mac OS X")) return "macOS";
        if (ua.contains("Android")) return "Android";
        if (ua.contains("iPhone") || ua.contains("iPad")) return "iOS";
        if (ua.contains("Linux")) return "Linux";
        if (ua.contains("CrOS")) return "Chrome OS";
        return "Unknown";
    }

    private String parseBrowser(String ua) {
        if (ua == null) return "Unknown";
        if (ua.contains("Edg/")) return "Edge";
        if (ua.contains("OPR/") || ua.contains("Opera")) return "Opera";
        if (ua.contains("Chrome/") && !ua.contains("Edg/") && !ua.contains("OPR/")) return "Chrome";
        if (ua.contains("Firefox/")) return "Firefox";
        if (ua.contains("Safari/") && !ua.contains("Chrome/")) return "Safari";
        return "Unknown";
    }

    private String parseDeviceType(String ua) {
        if (ua == null) return "Unknown";
        if (ua.contains("Mobile") || ua.contains("Android") && !ua.contains("Tablet")) return "Mobile";
        if (ua.contains("Tablet") || ua.contains("iPad")) return "Tablet";
        return "Desktop";
    }
}

