package attendance.dto;

import attendance.model.AttendanceType;
import attendance.model.Nickname;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceLogDto(
        Nickname nickname,
        LocalDate attendanceDate,
        LocalTime attendanceTime,
        AttendanceType attendanceType
) {
}
