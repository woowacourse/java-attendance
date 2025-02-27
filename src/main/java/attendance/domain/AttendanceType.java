package attendance.domain;


import java.util.Arrays;

public enum AttendanceType {
    ATTENDANCE("출석", 5),
    LATE("지각", 30),
    ABSENCE("결석", 31);

    private final String typeDescription;
    private final int typeDecisionValue;

    AttendanceType(String typeDescription, int typeDecisionValue) {
        this.typeDescription = typeDescription;
        this.typeDecisionValue = typeDecisionValue;
    }

    public static AttendanceType determineAttendanceTypeByLateTime(int lateTime) {
        return Arrays.stream(AttendanceType.values())
            .filter(attendanceType -> attendanceType.typeDecisionValue > lateTime)
            .findFirst()
            .orElse(ABSENCE);
    }

    public String getTypeDescription() {
        return typeDescription;
    }
}
