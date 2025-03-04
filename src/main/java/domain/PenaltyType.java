package domain;

import java.util.Map;

public enum PenaltyType {
    BAN("제적", 6),
    ONE_ON_ONE("면담", 3),
    WARNING("경고", 2),
    NONE("", 0),
    ;

    public static final int LATE_TO_ABSENCE_THRESHOLD = 3;
    private final String name;
    private final int requiredAbsenceCount;

    PenaltyType(String name, int requiredAbsenceCount) {
        this.name = name;
        this.requiredAbsenceCount = requiredAbsenceCount;
    }

    public String getName() {
        return name;
    }

    public static PenaltyType getFrom(Map<AttendanceType, Integer> attendanceTypeCount) {
        int totalAbsenceCount = attendanceTypeCount.getOrDefault(AttendanceType.ABSENCE, 0) +
                attendanceTypeCount.getOrDefault(AttendanceType.LATE, 0) / LATE_TO_ABSENCE_THRESHOLD;

        for (PenaltyType value : PenaltyType.values()) {
            if (totalAbsenceCount >= value.requiredAbsenceCount) {
                return value;
            }
        }

        return PenaltyType.NONE;
    }
}
