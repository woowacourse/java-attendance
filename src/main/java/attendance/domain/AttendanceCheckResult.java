package attendance.domain;

import java.util.List;
import java.util.Map;

public record AttendanceCheckResult (
    String name,
    List<Attendance>attendanceUntilYesterday,
    Map<AttendanceStatus, Integer>attendanceStatusCount,
    AttendancePenalty attendancePenalty
){
}
