package attendance.fixer;

import static attendance.domain.record.AttendanceType.ATTENDANCE;

import attendance.domain.record.AttendanceRecord;
import attendance.domain.record.AttendanceType;
import java.time.LocalDateTime;

public class RecordFixer {

    public static AttendanceRecord makeRecord(
            String nickname, AttendanceType attendanceType
    ) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, 9, 8, 10, 0);
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }

    public static AttendanceRecord makeRecord(
            String nickname, LocalDateTime arrivalDateTime
    ) {
        return new AttendanceRecord(nickname, arrivalDateTime, ATTENDANCE);
    }

    public static AttendanceRecord makeRecord(
            String nickname, LocalDateTime arrivalDateTime, AttendanceType attendanceType
    ) {
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }
}
