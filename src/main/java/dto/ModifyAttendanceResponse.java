package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ModifyAttendanceResponse(
        LocalDate date,
        LocalTime previousTime,
        LocalTime modifiedTime,
        String previousStatus,
        String modifiedStatus
) {
}