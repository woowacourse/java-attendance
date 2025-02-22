package domain;

import java.time.LocalDate;

public record AttendanceRecord(
        LocalDate date,
        AttendanceTime attendanceTime
) {

}
