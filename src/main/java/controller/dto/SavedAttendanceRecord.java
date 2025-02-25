package controller.dto;

import constant.AttendanceStatus;
import java.time.LocalDateTime;

public record SavedAttendanceRecord(
        LocalDateTime dateTime,
        AttendanceStatus status
) {

}
