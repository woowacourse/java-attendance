package dto;

import domain.AttendanceHistory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceHistoryDto(
        LocalDate attendanceDate,
        LocalTime attendanceTime,
        String result
) {
    public static AttendanceHistoryDto from(AttendanceHistory history) {
        Optional<LocalTime> optionalLocalTime = history.getAttendanceTime();
        LocalTime attendanceTime = optionalLocalTime.orElse(null);
        return new AttendanceHistoryDto(history.getAttendanceDate(), attendanceTime,
                history.getAttendanceResult().getResult());
    }
}
