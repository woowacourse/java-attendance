package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Attendance {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final int HOLIDAY = 25;

    LocalDateTime localDateTime;
    AttendanceStatus attendanceStatus;

    public static Attendance of(final String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
            return new Attendance(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 형식이 아닙니다.");
        }
    }

    public Attendance(final LocalDateTime localDateTime) {
        validateHoliday(localDateTime);
        Week day = Week.findByAttendanceTime(localDateTime);
        this.localDateTime = localDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, localDateTime.toLocalTime());
    }

    private void validateHoliday(LocalDateTime localDateTime) {
        final int day = localDateTime.getDayOfMonth();
        if (day == HOLIDAY) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
