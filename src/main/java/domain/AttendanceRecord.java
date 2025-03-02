package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AttendanceRecord {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    private AttendanceRecord(final LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = null;
    }

    public AttendanceRecord(final LocalDateTime attendanceDateTime) {
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
    }

    public static AttendanceRecord empty(final LocalDate attendanceDate) {
        return new AttendanceRecord(attendanceDate);
    }

    public AttendanceStatus calculateAttendanceStatus() {
        if (attendanceTime == null) {
            return AttendanceStatus.ABSENCE;
        }
        return AttendanceStatus.calculateStatus(attendanceTime, attendanceDate.getDayOfWeek());
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Optional<LocalTime> getAttendanceTime() {
        return Optional.ofNullable(attendanceTime);
    }
}
