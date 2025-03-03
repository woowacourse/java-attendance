package dto;

import domain.AttendanceHistory;
import domain.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public record EditResponseDto(
        LocalDate beforeAttendanceDate,
        LocalTime beforeAttendanceTime,
        String beforeResult,
        LocalDateTime afterAttendanceTime,
        String afterResult
) {
    public static EditResponseDto of(AttendanceHistory beforeHistory, LocalDateTime afterAttendanceTime,
                                     AttendanceResult afterResult) {
        LocalDate beforeDate = beforeHistory.getAttendanceDate();
        Optional<LocalTime> beforeTimeOptional = beforeHistory.getAttendanceTime();
        LocalTime beforeTime = beforeTimeOptional.orElse(null);
        String beforeResult = beforeHistory.getAttendanceResult().getResult();
        beforeHistory.getAttendanceTime();
        return new EditResponseDto(beforeDate, beforeTime, beforeResult, afterAttendanceTime, afterResult.getResult());
    }
}
