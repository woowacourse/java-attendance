package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Holiday {

    public static void validateAttendanceDate(final LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
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
