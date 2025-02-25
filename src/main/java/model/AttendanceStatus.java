package model;

public enum AttendanceStatus {

    ABSENT("결석", 30),
    ATTENDANCE("출석",0),
    LATE("지각", 5);

    private final String state;
    private final int attendanceJudgementTime;

    AttendanceStatus(String state, int attendanceJudgementTime) {
        this.state = state;
        this.attendanceJudgementTime = attendanceJudgementTime;
    }

    public String getState() {
        return state;
    }

    public static AttendanceStatus fromMinutesLate(int minutesLate) {
        if (minutesLate > ABSENT.attendanceJudgementTime) {
            return ABSENT;
        }
        if (minutesLate > LATE.attendanceJudgementTime) {
            return LATE;
        }
        return ATTENDANCE;
    }
}
