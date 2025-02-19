package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceManager {
    public static String checkAttendanceResult(DayOfWeek dayOfWeek, LocalTime localTime) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
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

    public static void checkHoliday(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY &&
                !localDate.equals(LocalDate.of(2024, 12, 25))) {
            return;
        }
        throw new IllegalArgumentException("등교일이 아닙니다");
    }
}
