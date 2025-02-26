package service.dto;

import java.util.List;

public record MonthAttendanceStatisticsResponse(
        List<AttendanceRecordResponse> attendanceRecords,
        AttendanceStatusCount attendanceStatusCount,
        String riskRank
) {

}
