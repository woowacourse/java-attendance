package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Holidays {

    private final Set<LocalDate> holidays = new HashSet<>();

    public void addHoliday(final LocalDate date) {
        holidays.add(date);
    }

    public boolean isHoliday(final LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || holidays.contains(date);
    }

    public void validateAttendanceDate(final LocalDate attendanceDate) {
        if (isHoliday(attendanceDate)) {
            throw new IllegalArgumentException(formatErrorMessage(attendanceDate));
        }
    }

    private static String formatErrorMessage(final LocalDate date) {
        return String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
