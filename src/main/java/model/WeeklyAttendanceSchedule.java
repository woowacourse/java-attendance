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
        Holiday.validateHoliday(localDate);
        return Arrays.stream(WeeklyAttendanceSchedule.values())
                .filter(weeklyAttendanceSchedule -> weeklyAttendanceSchedule.dayOfWeek.equals(localDate.getDayOfWeek()))
                .map(WeeklyAttendanceSchedule::getAttendanceStartTime)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("[ERROR] 입력받은 %d월 %d일 %s의 attendanceStartTime을 찾지 못하였습니다.",
                                localDate.getMonthValue(),
                                localDate.getDayOfMonth(),
                                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)))
                );
    }

    public LocalTime getAttendanceStartTime() {
        return attendanceStartTime;
    }
}