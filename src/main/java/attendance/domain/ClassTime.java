package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public enum ClassTime {
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0));

    private final LocalTime startTime;

    ClassTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    public static int calculateAttendanceDifference(final LocalDateTime attendanceDateTime) {
        ClassTime classTime = find(attendanceDateTime.toLocalDate());
        return calculateDifferenceTime(
                attendanceDateTime.toLocalTime(),
                classTime.startTime
        );
    }

    private static ClassTime find(final LocalDate date) {
        return Arrays.stream(values())
                .filter(weekday -> isSameDayOfWeek(date, weekday))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(formatErrorMessage(date)));
    }

    private static boolean isSameDayOfWeek(final LocalDate date, final ClassTime weekday) {
        return weekday.name().equals(date.getDayOfWeek().name());
    }

    private static int calculateDifferenceTime(final LocalTime time, final LocalTime comparisonTime) {
        int differenceInSeconds = time.toSecondOfDay() - comparisonTime.toSecondOfDay();
        return differenceInSeconds / 60;
    }

    private static String formatErrorMessage(final LocalDate date) {
        return String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
