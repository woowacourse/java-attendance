package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTime {

    private final LocalDate date;
    private Integer hour;
    private Integer minute;

    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    public AttendanceTime(final LocalDate date, final Integer hour, final Integer minute) {

        validateAttendDate(date);
        validateOperatingTime(hour, minute);
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    public AttendanceTime(final LocalDate date) {

        validateAttendDate(date);
        this.date = date;
        this.hour = null;
        this.minute = null;
    }

    public static boolean isWeekend(final DayOfWeek day) {

        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public boolean isMonday() {

        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    public boolean isBefore(final LocalTime localTime) {

        return LocalTime.of(hour, minute).isBefore(localTime);
    }

    public boolean isSameDay(final LocalDate localDate) {

        return date.isEqual(localDate);
    }

    public boolean isDefaultAbsent() {

        return hour == null && minute == null;
    }

    public void modify(final Integer targetHour, final Integer targetMinute) {

        this.hour = targetHour;
        this.minute = targetMinute;
    }

    public int getHour() {

        return hour;
    }

    public int getMinute() {

        return minute;
    }

    public LocalDate getDate() {

        return LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    private void validateAttendDate(final LocalDate date) {

        if (isWeekend(date.getDayOfWeek())) {
            throw new IllegalArgumentException("[ERROR] 등교 날짜가 아닙니다.");
        }
    }

    private void validateOperatingTime(final Integer hour, final Integer minute) {

        LocalTime inputTime = LocalTime.of(hour, minute);

        if (inputTime.isBefore(CAMPUS_START_TIME) || inputTime.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }
}
