package domain;

import java.util.List;

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

    public static Penalty from(List<AttendanceStatus> attendanceStatuses) {
        int absenceCount = 0;
        int perceptionCount = 0;

        for (AttendanceStatus status : attendanceStatuses) {
            absenceCount += countAbsence(status);
            perceptionCount += countPerception(status);

            absenceCount += convertPerceptionToAbsence(perceptionCount);
            perceptionCount = resetPerceptionIfConverted(perceptionCount);
        }

        return determinePenalty(absenceCount);
    }

    private static int countAbsence(AttendanceStatus status) {
        if (status.isAbsence()) {
            return 1;
        }

        return 0;
    }

    private static int countPerception(AttendanceStatus status) {
        if (status.isPerception()) {
            return 1;
        }

        return 0;
    }

    private static int convertPerceptionToAbsence(int perceptionCount) {
        if (perceptionCount >= 3) {
            return 1;
        }

        return 0;
    }

    private static int resetPerceptionIfConverted(int perceptionCount) {
        if (perceptionCount >= 3) {
            return 0;
        }

        return perceptionCount;
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
