package dto;

import domain.AttendanceStatus;
import java.util.List;

public record TotalRecordsResponse(
        int attendanceCount,
        int lateCount,
        int absentCount
) {
    public static TotalRecordsResponse fromAttendanceRecords(List<AttendanceRecordResponse> records) {
        List<AttendanceStatus> statuses = records.stream().map(record -> record.attendanceStatus()).toList();
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
            if (status == AttendanceStatus.ABSENT) {
                absentCount++;
            }
        }

        return new TotalRecordsResponse(attendanceCount, lateCount, absentCount);
    }
}
