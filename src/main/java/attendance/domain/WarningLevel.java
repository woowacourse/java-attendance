package attendance.domain;

import java.util.Map;

public enum WarningLevel {
    ONE_ON_ONE, WARNING, NONE, EXPELLED;

    public static WarningLevel calculateBy(final Map<AttendanceStatus, Integer> attendanceStatusCounts) {
       int absentCount = attendanceStatusCounts.get(AttendanceStatus.ABSENT);
       int lateCount = attendanceStatusCounts.get(AttendanceStatus.LATE);
       absentCount += lateCount / 3;
       if (absentCount > 5) {
           return EXPELLED;
       }
       if (absentCount >= 3) {
           return ONE_ON_ONE;
       }
       if (absentCount >= 2) {
           return WARNING;
       }
       return NONE;
    }
}
