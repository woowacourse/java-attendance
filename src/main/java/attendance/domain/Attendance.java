package attendance.domain;

import java.time.LocalDateTime;

public record Attendance(AttendanceRecord record, AttendanceStatus status) {
    public Attendance(final LocalDateTime localDateTime) {
        this(new AttendanceRecord(localDateTime), Campus.calculateAttendanceStatus(localDateTime));
    }

    public boolean isSameDay(final Attendance attendance) {
        return record.date().equals(attendance.record().date());
    }
}
