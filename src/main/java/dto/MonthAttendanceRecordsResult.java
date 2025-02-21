package dto;

import domain.AttendanceStatusStatistics;
import domain.Manage;
import java.util.List;

public record MonthAttendanceRecordsResult(
        String nickname,
        List<AttendanceRecord> history,
        AttendanceStatusStatistics attendanceStatusStatistics,
        Manage manage
) {
    public static MonthAttendanceRecordsResult of(String nickname, List<AttendanceRecord> attendanceRecords,
                                                  AttendanceStatusStatistics attendanceStatusStatistics,
                                                  Manage manage) {
        attendanceRecords.forEach(
                record -> attendanceRecords.add(new AttendanceRecord(record.date(), record.time(), record.status())));
        return new MonthAttendanceRecordsResult(nickname, attendanceRecords, attendanceStatusStatistics, manage);
    }
}
