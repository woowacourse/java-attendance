package domain;

import java.util.List;

public class AttendanceResults {

    private final List<AttendanceResult> attendanceResults;

    AttendanceResults(List<AttendanceResult> attendanceResults) {
        this.attendanceResults = attendanceResults;
    }

    public AttendCount countAttendStatus() {
        long attendCount = countStatus(AttendStatus.ATTEND);
        long lateCount = countStatus(AttendStatus.LATE);
        long absenceCount = countStatus(AttendStatus.ABSENCE);
        return new AttendCount(attendCount, lateCount, absenceCount);
    }

    private long countStatus(AttendStatus attendStatus) {
        return attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendStatus() == attendStatus)
                .count();
    }

    public List<AttendanceResult> getAttendanceResults() {
        return attendanceResults;
    }
}
