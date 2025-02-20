package service.dto;

import domain.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceModifyResponse(
        LocalDateTime beforeTime,
        AttendanceStatus beforeStatus,
        LocalDateTime afterTime,
        AttendanceStatus afterStatus
) {
}
