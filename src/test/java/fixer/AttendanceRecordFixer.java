package fixer;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatusType;
import java.time.LocalDateTime;

public class AttendanceRecordFixer {

    public static AttendanceRecord makeRecord(
            String nickname, AttendanceStatusType attendanceType
    ) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, 9, 8, 10, 0);
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }

    public static AttendanceRecord makeRecord(
            String nickname, LocalDateTime arrivalDateTime, AttendanceStatusType attendanceType
    ) {
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }
}
