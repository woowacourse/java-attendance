package dto;

import domain.AttendanceStatus;
import domain.Manage;
import java.util.List;
import java.util.Map;

public record MonthAttendanceRecordsResult(
        String nickname,
        List<AttendanceRecord> history,
        Map<AttendanceStatus, Integer> statusCounter,
        Manage manage
) {
    public static MonthAttendanceRecordsResult of(String nickname, List<AttendanceRecord> attendanceRecords,
                                                  Map<AttendanceStatus, Integer> statusCounter, Manage manage) {
        attendanceRecords.forEach(
                record -> attendanceRecords.add(new AttendanceRecord(record.date(), record.time(), record.status())));
        return new MonthAttendanceRecordsResult(nickname, attendanceRecords, statusCounter, manage);
    }
}
