package domain;

import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Attendance {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    LocalDateTime localDateTime;
    AttendanceStatus attendanceStatus;

    public Attendance(final LocalDateTime localDateTime) {
        Week day = Week.findByAttendanceTime(localDateTime);
        this.localDateTime = localDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, localDateTime.toLocalTime());
    }

    public static Attendance of(final String inputTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(inputTime, formatter);
            return new Attendance(dateTime);
        } catch (DateTimeParseException e) {
            throw new CustomIllegalArgumentException("올바른 형식이 아닙니다.");
        }
    }

    public boolean equals(final LocalDate findLocalDate) {
        return AttendanceDateTime.getDate(localDateTime)
                .equals(findLocalDate);
    }

    public int getDateOfMonth() {
        return localDateTime.getDayOfMonth();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }
}
