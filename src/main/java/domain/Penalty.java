package domain;

import java.util.List;

public enum Penalty {
    NONE("없음"),
    WARNING("경고"),
    INTERVIEW("면담"),
    WEEDING("제적");

    private final String name;

    Penalty(String name) {
        this.name = name;
    }

    public static Penalty calculatePenalty(List<AttendanceStatus> attendanceStatuses) {
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
        if (absenceCount > 5) {
            return WEEDING;
        }

        if (absenceCount >= 3) {
            return INTERVIEW;
        }

        if (absenceCount >= 2) {
            return WARNING;
        }

        return NONE;
    }

    public String getName() {
        return name;
    }
}
