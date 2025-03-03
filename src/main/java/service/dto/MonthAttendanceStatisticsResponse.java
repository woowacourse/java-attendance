package service.dto;

import domain.AbstractAttendanceRecord;
import domain.AttendanceStatusCount;
import domain.RiskRank;
import java.util.List;

public record MonthAttendanceStatisticsResponse(
        List<AbstractAttendanceRecord> attendanceRecords,
        AttendanceStatusCount attendanceStatusCount,
        RiskRank riskRank
) {

}
