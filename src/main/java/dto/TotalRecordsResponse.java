package dto;

import domain.AttendanceStatus;
import java.util.List;

public record TotalRecordsResponse(
        int attendanceCount,
        int lateCount,
        int absentCount
) {

    public static final int DECEMBER_DAYS_COUNT = 31;

    public static TotalRecordsResponse fromAttendanceRecords(List<AttendanceRecordResponse> records) {
        List<AttendanceStatus> statuses = records.stream().map(AttendanceRecordResponse::attendanceStatus).toList();
        int attendanceCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (AttendanceStatus status : statuses) {
            if (status == AttendanceStatus.ATTEND) {
                attendanceCount++;
            }
            if (status == AttendanceStatus.LATE) {
                lateCount++;
            }
            absentCount = 31 - lateCount - attendanceCount;
        }

        return new TotalRecordsResponse(attendanceCount, lateCount, absentCount);
    }
}
