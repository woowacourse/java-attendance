package domain.attendance;

import domain.holiday.Holiday;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceSchedule {
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0)),
    WEDNESDAY(LocalTime.of(10, 0)),
    THURSDAY(LocalTime.of(10, 0)),
    FRIDAY(LocalTime.of(10, 0)),
    ;

    private final LocalTime startTime;

    AttendanceSchedule(LocalTime startTime) {
        this.startTime = startTime;
    }

    public static int calculateLateMinutes(LocalDate date, LocalTime time) {
        if (Holiday.isWeekendOrHoliday(date)) {
            throw new IllegalArgumentException("출석 시간이 없는 요일입니다.");
        }

        AttendanceSchedule schedule = getScheduleForDay(date);
        if (time.isAfter(schedule.startTime)) {
            Duration duration = Duration.between(schedule.startTime, time);
            return Math.toIntExact(duration.toMinutes());
        }
        return 0;
    }

    private static AttendanceSchedule getScheduleForDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return Arrays.stream(AttendanceSchedule.values())
                .filter(schedule -> schedule.name().equals(dayOfWeek.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("출석 시간이 없는 요일입니다."));
    }
}
