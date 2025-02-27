package domain;

import static util.Constants.*;

import dto.AttendanceCount;

public enum Penalty {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NONE("X");

    private final String expression;

    Penalty(String expression) {
        this.expression = expression;
    }

    public static Penalty from(AttendanceCount attendanceCount) {
        int consideredAbsentCount = attendanceCount.absentCount() + (attendanceCount.lateCount() / 3);

        if(consideredAbsentCount >= EXPULSION_CONDITION) {
            return Penalty.EXPULSION;
        }
        if (consideredAbsentCount >= COUNSELING_CONDITION) {
            return Penalty.COUNSELING;
        }
        if (consideredAbsentCount >= WARNING_CONDITION) {
            return Penalty.WARNING;
        }
        return NONE;
    }
}
