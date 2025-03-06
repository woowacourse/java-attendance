package attendance.domain;

import java.time.LocalDateTime;

public record Attendance(AttendanceRecord record, AttendanceStatus status) {
    private Attendance(final LocalDateTime localDateTime) {
        this(new AttendanceRecord(localDateTime), Campus.calculateAttendanceStatus(localDateTime));
    }

    public static Attendance of(final LocalDateTime localDateTime) {
        validateOperationDay(localDateTime);
        return new Attendance(localDateTime);
    }

    private static void validateOperationDay(final LocalDateTime localDateTime) {
        Campus.validateOperationDay(localDateTime);
    }

    public boolean isSameDay(final Attendance attendance) {
        return record.date().equals(attendance.record().date());
    }
}
