package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    public static final int WEEKDAY = 1;
    private final AttendanceDate date;

    public Attendance(LocalDateTime dateTime) {
        validate(dateTime);

        this.date = new BasicAttendanceDate(dateTime.toLocalDate(),
                AttendanceStatus.calculateStatus(dateTime),
                dateTime.toLocalTime());
    }

    public Attendance(LocalDate date) {
        validate(date);
        this.date = new EmptyAttendanceDate(date, AttendanceStatus.ABSENCE);
    }

    public boolean has(LocalDate day) {
        return this.date.getDate().isEqual(day);
    }

    public AttendanceDate getDate() {
        return date;
    }

    public LocalDateTime getAttendanceDateTime() {
        return this.date.getDateTime();
    }

    public AttendanceStatus getStatus() {
        return this.date.getStatus();
    }

    private void validate(LocalDateTime dateTime) {
        validate(dateTime.toLocalDate());
        if (!AttendanceTime.isOperatingTime(dateTime.toLocalTime())) {
            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석할 수 있습니다");
        }
    }

    private void validate(LocalDate date) {
        if (Holiday.isHoliday(date) ||
                date.getDayOfWeek().compareTo(DayOfWeek.FRIDAY) > WEEKDAY) {
            throw new IllegalArgumentException("주말 또는 공휴일에는 출석할 수 없습니다");
        }
    }
}
