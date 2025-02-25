package domain.attendance;

import java.time.LocalDateTime;

public enum AttendanceState {
    ATTENDANCE("출석"),
    TARDY("지각"),
    ABSENCE("결석"),
    ;

    private final String state;

    AttendanceState(String state) {
        this.state = state;
    }

    public static AttendanceState calculateAttendanceState(int dayOfWeek, LocalDateTime dateTime) {
        if (AttendanceTime.isAttendance(dayOfWeek, dateTime)) {
            return ATTENDANCE;
        }
        if (AttendanceTime.isAbsence(dayOfWeek, dateTime)) {
            return ABSENCE;
        }
        return TARDY;
    }

    public String getState() {
        return state;
    }
}
