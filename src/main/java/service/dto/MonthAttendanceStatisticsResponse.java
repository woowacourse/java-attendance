package service.dto;

import domain.AbstractAttendanceRecord;
import domain.RiskRank;
import java.util.List;

public record MonthAttendanceStatisticsResponse(
        List<AbstractAttendanceRecord> attendanceRecords,
        AttendanceStatusCount attendanceStatusCount,
        RiskRank riskRank
) {

    public record AttendanceStatusCount(
            int attendanceCount,
            int lateCount,
            int absentCount
    ) {

    }
}
