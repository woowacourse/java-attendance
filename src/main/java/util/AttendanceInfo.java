package util;

import domain.AttendanceTime;
import java.time.LocalDate;

public record AttendanceInfo(
        String crewName,
        LocalDate attendanceDate,
        AttendanceTime attendanceTime
) {

}
