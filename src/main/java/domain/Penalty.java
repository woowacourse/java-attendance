package domain;

import java.util.Map;

public enum Penalty {
    NONE("없음", 0),
    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    WEEDING("제적", 5);

    private final String name;
    private final int absenceCount;

    Penalty(String name, int absenceCount) {
        this.name = name;
        this.absenceCount = absenceCount;
    }

    public static Penalty from(Map<AttendanceStatus, Integer> attendanceStatusCount) {
        int absenceCount = calculateAbsenceCount(attendanceStatusCount);
        return determinePenalty(absenceCount);
    }

    public static int calculateAbsenceCount(Map<AttendanceStatus, Integer> attendanceStatusCount) {
        int absenceCount = attendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0);
        int perceptionCount = attendanceStatusCount.getOrDefault(AttendanceStatus.PERCEPTION, 0);

        absenceCount += (perceptionCount / 3);

        return absenceCount;
    }

    private static Penalty determinePenalty(int absenceCount) {
        if (absenceCount > WEEDING.absenceCount) {
            return WEEDING;
        }

        if (absenceCount >= INTERVIEW.absenceCount) {
            return INTERVIEW;
        }

        if (absenceCount >= WARNING.absenceCount) {
            return WARNING;
        }

        return NONE;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public String getName() {
        return name;
    }
}
