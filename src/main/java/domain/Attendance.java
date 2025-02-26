package domain;

import java.time.LocalTime;

public class Attendance {
    private static final LocalTime PRESENT_THRESHOLD_TIME = LocalTime.of(10, 5);
    private static final LocalTime TARDY_THRESHOLD_TIME = LocalTime.of(10, 30);

    public String checkAttendance(String nickname, LocalTime time) {
        if (time.isBefore(PRESENT_THRESHOLD_TIME) || time.equals(PRESENT_THRESHOLD_TIME)) {
            return "출석";
        }
        if (time.isBefore(TARDY_THRESHOLD_TIME) || time.equals(TARDY_THRESHOLD_TIME)) {
            return "지각";
        }
        return "결석";
    }
}
