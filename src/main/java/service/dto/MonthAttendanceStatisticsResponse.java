package service.dto;

import domain.AbstractAttendanceRecord;
import domain.AttendanceStatus;
import domain.RiskRank;
import java.util.List;
import java.util.Map;

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

        public static AttendanceStatusCount of(Map<AttendanceStatus, Integer> statusCount) {
            return new AttendanceStatusCount(
                    statusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0),
                    statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                    statusCount.getOrDefault(AttendanceStatus.ABSENT, 0)
            );
        }
    }
}
