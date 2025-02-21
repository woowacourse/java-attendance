package attendance.model;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class CustomLocalDateTime {
    private static Clock clock = Clock.system(ZoneId.of("UTC"));
    private static final Set<LocalDate> holidayDate = Set.of(LocalDate.of(2024, 12, 25));
    private static final DateTimeFormatter MODIFY_ATTENDANCE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static LocalDate nowDate() {
        return now().toLocalDate();
    }

    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, MODIFY_ATTENDANCE_TIME_FORMATTER);
    }

    public static LocalDateTime generateAttendanceDateTime(String time) {
        return LocalDateTime.of(
                nowDate(),
                CustomLocalDateTime.parseTime(time)
        );
    }

    public static boolean isTodayHoliday() {
        return isHoliday(nowDate());
    }

    public static String formatNowDateTime(String pattern) {
        return now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static boolean isHoliday(LocalDate localDate) {
        return localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                holidayDate.contains(localDate);
    }
}
