package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AttendanceManager {
    public static String checkAttendanceResult(DayOfWeek dayOfWeek, LocalTime localTime) {
        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            if (localTime.isBefore(LocalTime.of(13, 6))) {
                return "출석";
            }

            if (localTime.isBefore(LocalTime.of(13, 31))) {
                return "지각";
            }
            return "결석";
        }
        if (localTime.isBefore(LocalTime.of(10, 6))) {
            return "출석";
        }

        if (localTime.isBefore(LocalTime.of(10, 31))) {
            return "지각";
        }
        return "결석";
    }
}
