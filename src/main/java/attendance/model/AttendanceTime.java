package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTime {
    private final LocalDate date;
    private final int hour;
    private final int minute;

    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    public AttendanceTime(final LocalDate date, final int hour, final int minute) {

        validateAttendDate(date);
        validateOperatingTime(hour, minute);
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    private void validateAttendDate(final LocalDate date) {

        if (isWeekend(date.getDayOfWeek())) {
            throw new IllegalArgumentException("[ERROR] 등교 날짜가 아닙니다.");
        }
    }

    private boolean isWeekend(final DayOfWeek day) {

        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private void validateOperatingTime(final int hour, final int minute) {

        LocalTime inputTime = LocalTime.of(hour, minute);

        if (inputTime.isBefore(CAMPUS_START_TIME) || inputTime.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isMonday() {

        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    public boolean isBefore(final LocalTime localTime) {

        return LocalTime.of(hour, minute).isBefore(localTime);
    }
}
