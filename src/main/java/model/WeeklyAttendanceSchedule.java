package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public enum WeeklyAttendanceSchedule {
    MONDAY(LocalTime.of(13, 0), DayOfWeek.MONDAY),
    TUESDAY(LocalTime.of(10, 0), DayOfWeek.TUESDAY),
    WEDNESDAY(LocalTime.of(10, 0), DayOfWeek.WEDNESDAY),
    THURSDAY(LocalTime.of(10, 0), DayOfWeek.THURSDAY),
    FRIDAY(LocalTime.of(10, 0), DayOfWeek.FRIDAY);

    private final LocalTime attendanceStartTime;
    private final DayOfWeek dayOfWeek;

    WeeklyAttendanceSchedule(LocalTime attendanceStartTime, DayOfWeek dayOfWeek) {
        this.attendanceStartTime = attendanceStartTime;
        this.dayOfWeek = dayOfWeek;
    }

    public static LocalTime findAttendanceScheduleByLocalDate(LocalDate localDate) {
        validateHoliday(localDate);
        return Arrays.stream(WeeklyAttendanceSchedule.values())
                .filter(weeklyAttendanceSchedule -> weeklyAttendanceSchedule.dayOfWeek.equals(localDate.getDayOfWeek()))
                .map(WeeklyAttendanceSchedule::getAttendanceStartTime)
                .findFirst()
                .orElseThrow();
    }

    public static boolean checkHoliday(LocalDate date) {
        return date.equals(LocalDate.of(2024, 12, 25)) ||
                Arrays.stream(WeeklyAttendanceSchedule.values())
                        .noneMatch(
                                weeklyAttendanceSchedule ->
                                        weeklyAttendanceSchedule.getDayOfWeek().equals(date.getDayOfWeek())
                        );
    }

    private static void validateHoliday(LocalDate localDate) {
        int month = localDate.getMonthValue();
        int date = localDate.getDayOfMonth();
        String day = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);

        if (localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                localDate.equals(LocalDate.of(2024, 12, 25))) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", month, date, day));
        }
    }

    public LocalTime getAttendanceStartTime() {
        return attendanceStartTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}