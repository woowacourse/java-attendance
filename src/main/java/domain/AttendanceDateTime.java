package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;

public class AttendanceDateTime {

    private static final int MONDAY_STANDARD_HOUR = 13;
    private static final int DEFAULT_STANDARD_HOUR = 10;

    private static final int LATE_MINUTE_THRESHOLD = 5;
    private static final int ABSENT_MINUTE_THRESHOLD = 30;

    private static final int POSSIBLE_ATTENDANCE_START_HOUR = 8;
    private static final int POSSIBLE_ATTENDANCE_END_HOUR = 23;

    public static final MonthDay CHRISTMAS = MonthDay.of(12, 25);

    private LocalDateTime attendanceDateTime;

    private AttendanceDateTime(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public static AttendanceDateTime from(LocalDateTime attendanceDateTime) {
        validate(attendanceDateTime);
        return new AttendanceDateTime(attendanceDateTime);
    }

    public static void validate(LocalDateTime attendanceDateTime) {
        LocalDate date = attendanceDateTime.toLocalDate();
        LocalTime time = attendanceDateTime.toLocalTime();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        validateHoliday(date);
        validateWeekend(dayOfWeek);
        validateOperatingTime(time);
    }

    public void update(LocalTime updateTime) {
        validateOperatingTime(updateTime);
        this.attendanceDateTime = LocalDateTime.of(this.attendanceDateTime.toLocalDate(), updateTime);
    }

    public AttendanceState check() {
        DayOfWeek dayOfWeek = attendanceDateTime.getDayOfWeek();
        int hour = attendanceDateTime.getHour();
        int minute = attendanceDateTime.getMinute();

        return decideAttendanceState(dayOfWeek, hour, minute);
    }

    public boolean isCorrectDay(AttendanceSheet attendanceSheet) {
        return this.attendanceDateTime.toLocalDate()
                .equals(attendanceSheet.getAttendanceDateTime().attendanceDateTime.toLocalDate());
    }

    public boolean isCorrectDay(int day) {
        return attendanceDateTime.getDayOfMonth() == day;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    private AttendanceState decideAttendanceState(DayOfWeek dayOfWeek, int hour, int minute) {
        int standardHour = getStandardHour(dayOfWeek);

        if (isAbsent(hour, minute, standardHour)) {
            return AttendanceState.ABSENT;
        }

        if (isLate(hour, minute, standardHour)) {
            return AttendanceState.LATE;
        }

        return AttendanceState.ATTEND;
    }

    private int getStandardHour(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY ? MONDAY_STANDARD_HOUR : DEFAULT_STANDARD_HOUR;
    }

    private boolean isAbsent(int hour, int minute, int standardHour) {
        return hour > standardHour || (hour == standardHour && minute > ABSENT_MINUTE_THRESHOLD);
    }

    private boolean isLate(int hour, int minute, int standardHour) {
        return hour == standardHour && minute > LATE_MINUTE_THRESHOLD;
    }

    private static void validateOperatingTime(LocalTime time) {
        if (time.getHour() < POSSIBLE_ATTENDANCE_START_HOUR || time.getHour() == POSSIBLE_ATTENDANCE_END_HOUR) {
            throw new IllegalArgumentException("[ERROR] 출석 시간이 아닙니다.");
        }
    }

    private static void validateWeekend(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 출석할 수 없습니다.");
        }
    }

    private static void validateHoliday(LocalDate date) {
        if (MonthDay.from(date).equals(CHRISTMAS)) {
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출석할 수 없습니다.");
        }
    }

}
