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
        int attendanceCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (AttendanceRecordResponse record: records) {
            AttendanceStatus status = record.attendanceStatus();

            if (status == AttendanceStatus.ATTEND) {
                attendanceCount++;
            }
            if (status == AttendanceStatus.LATE) {
                lateCount++;
            }
            absentCount = DECEMBER_DAYS_COUNT - lateCount - attendanceCount;
        }

        return new TotalRecordsResponse(attendanceCount, lateCount, absentCount);
    }
}
