package dto.result;

import java.util.List;

public record MemberAttendResult(
        String name,
        List<AttendResult> attendanceResults,
        int attendCount,
        int lateCount,
        int absentCount,
        String interviewee
) {
}
