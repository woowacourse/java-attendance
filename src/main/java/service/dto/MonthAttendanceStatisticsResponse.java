package service.dto;

import java.util.List;
import java.util.Map;

public record MonthAttendanceStatisticsResponse(
        List<AttendanceRecordResponse> attendanceRecords,
        Map<String, Integer> attendanceStatusCount,
        String riskRank
) {

}
