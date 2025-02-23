package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Objects;

public class AttendanceDateTime {

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

        if (dayOfWeek == DayOfWeek.MONDAY) {
            return decisionByHour(hour, minute, 13);
        }
        return decisionByHour(hour, minute, 10);
    }

    private static void validateOperatingTime(LocalTime time) {
        if (time.getHour() < 8 || time.getHour() == 23) {
            throw new IllegalArgumentException("[ERROR] 출석 시간이 아닙니다.");
        }
    }

    private AttendanceState decisionByHour(int hour, int minute, int standardHour) {
        if (hour == standardHour) {
            return decisionByMinute(minute);
        }

        if (hour > standardHour) {
            return AttendanceState.ABSENT;
        }

        return AttendanceState.ATTEND;
    }

    private AttendanceState decisionByMinute(int minute) {
        if (minute > 30) {
            return AttendanceState.ABSENT;
        }

        if (minute > 5) {
            return AttendanceState.LATE;
        }

        return AttendanceState.ATTEND;
    }

    private static void validateWeekend(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 출석할 수 없습니다.");
        }
    }

    private static void validateHoliday(LocalDate date) {
        if (MonthDay.from(date).equals(MonthDay.of(12, 25))) {
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출석할 수 없습니다.");
        }
    }

    public boolean isSame(AttendanceSheet attendanceSheet) {
        return this.attendanceDateTime.toLocalDate().equals(attendanceSheet.getAttendanceDateTime().attendanceDateTime.toLocalDate());
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceDateTime that = (AttendanceDateTime) object;
        return Objects.equals(getAttendanceDateTime(), that.getAttendanceDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAttendanceDateTime());
    }
}
