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
    
}
