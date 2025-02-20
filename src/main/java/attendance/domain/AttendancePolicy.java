package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {
    public static AttendanceType checkAttendanceType(LocalDate localDate, LocalTime localTime) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            if (localTime.isBefore(LocalTime.of(13, 6))) {
                return ATTENDANCE;
            }

            if (localTime.isBefore(LocalTime.of(13, 31))) {
                return LATE;
            }
            return ABSENCE;
        }
        if (localTime.isBefore(LocalTime.of(10, 6))) {
            return ATTENDANCE;
        }

        if (localTime.isBefore(LocalTime.of(10, 31))) {
            return LATE;
        }
        return ABSENCE;
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
