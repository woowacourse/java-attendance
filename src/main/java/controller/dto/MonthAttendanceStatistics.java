package controller.dto;

import java.util.List;
import java.util.Map;

public record MonthAttendanceStatistics(
        List<AttendanceRecordResponse> attendanceRecords,
        Map<String, Integer> attendanceStatusCount,
        String riskRank
) {

}
