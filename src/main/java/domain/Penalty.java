package domain;

import static util.Constants.EXPULSION_CONDITION;

import dto.AttendanceCount;

public enum Penalty {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고");

    private final String expression;

    Penalty(String expression) {
        this.expression = expression;
    }

    public static Penalty from(AttendanceCount attendanceCount) {
        if(attendanceCount.absentCount() >= EXPULSION_CONDITION) {
            return Penalty.EXPULSION;
        }
        return null;
    }
}
