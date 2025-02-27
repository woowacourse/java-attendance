package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRecord {

    private final LocalDateTime attendanceDateTime;
    private final boolean isEmpty;

    public AttendanceRecord(final LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
        this.isEmpty = false;
    }

    private AttendanceRecord(final LocalDateTime attendanceDateTime, final boolean isEmpty) {
        this.attendanceDateTime = attendanceDateTime;
        this.isEmpty = isEmpty;
    }

    public static AttendanceRecord empty(final LocalDate date) {
        final LocalDateTime emptyTime = LocalDateTime.of(date, LocalTime.NOON);
        return new AttendanceRecord(emptyTime, true);
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public AttendanceStatus calculateAttendanceStatus() {
        return AttendanceStatus.calculateStatus(attendanceDateTime.toLocalTime(), attendanceDateTime.getDayOfWeek());
    }

    public LocalDateTime getDateTime() {
        return this.attendanceDateTime;
    }
}
