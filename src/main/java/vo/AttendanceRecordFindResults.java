package vo;

import domain.ExpelWarning;

import java.util.Set;

public record AttendanceRecordFindResults(
        Set<AttendanceRecord> attendResults,
        int attendCount,
        int lateCount,
        int absentCount,
        ExpelWarning expelWarning
) {
}
