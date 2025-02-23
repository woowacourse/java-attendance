package dto.result;

import domain.ExpelRisk;

import java.util.List;

public record MemberAttendResult(
        String name,
        List<AttendResult> attendanceResults,
        int attendCount,
        int lateCount,
        int absentCount,
        ExpelRisk expelRisk
) {
}
