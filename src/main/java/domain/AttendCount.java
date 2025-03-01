package domain;

import java.util.List;

public record AttendCount(long attendCount, long lateCount, long absenceCount) {
    public static AttendCount createCount(final List<AttendStatus> attendStatus) {
        long attendCount = countStatus(attendStatus, AttendStatus.ATTEND);
        long lateCount = countStatus(attendStatus, AttendStatus.LATE);
        long absenceCount = countStatus(attendStatus, AttendStatus.ABSENCE);
        return new AttendCount(attendCount, lateCount, absenceCount);
    }

    private static long countStatus(List<AttendStatus> attendStatus, AttendStatus targetStatus) {
        return attendStatus.stream()
                .filter(status -> status.equals(targetStatus))
                .count();
    }
}
