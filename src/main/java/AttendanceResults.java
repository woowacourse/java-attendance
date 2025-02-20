import java.util.List;

public class AttendanceResults {

    private final List<AttendanceResult> attendanceResults;

    AttendanceResults(List<AttendanceResult> attendanceResults) {
        this.attendanceResults = attendanceResults;
    }

    public AttendCount countAttendStatus() {
        long attendCount = attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendStatus() == AttendStatus.ATTEND)
                .count();
        long lateCount = attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendStatus() == AttendStatus.LATE)
                .count();
        long absenceCount = attendanceResults.stream()
                .filter(attendanceResult -> attendanceResult.attendStatus() == AttendStatus.ABSENCE)
                .count();
        return new AttendCount(attendCount, lateCount, absenceCount);
    }
}
