package dto;

import domain.AttendanceRecord;
import domain.AttendanceStatusStatistics;
import domain.Manage;
import java.util.List;

public record MonthRecord(
        String nickname,
        List<AttendanceRecord> attendanceRecords,
        AttendanceStatusStatistics attendanceStatusStatistics,
        Manage manage
) {

}
