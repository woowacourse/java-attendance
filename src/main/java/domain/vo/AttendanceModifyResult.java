package domain.vo;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceModifyResult(
        LocalDate date,
        Optional<LocalTime> oldAttendTime,
        Optional<AttendanceStatus> oldStatus,
        LocalTime newAttendTime,
        AttendanceStatus newStatus
) {
}
