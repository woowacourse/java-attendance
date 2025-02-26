package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private static final LocalTime PRESENT_THRESHOLD_TIME = LocalTime.of(10, 5);
    private static final LocalTime TARDY_THRESHOLD_TIME = LocalTime.of(10, 30);
    private static final LocalTime MONDAY_PRESENT_THRESHOLD_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_TARDY_THRESHOLD_TIME = LocalTime.of(13, 30);

    public String checkAttendance(String nickname, LocalTime time, LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            if (time.isBefore(MONDAY_PRESENT_THRESHOLD_TIME) || time.equals(MONDAY_PRESENT_THRESHOLD_TIME)) {
                return "출석";
            }
            if (time.isBefore(MONDAY_TARDY_THRESHOLD_TIME) || time.equals(MONDAY_TARDY_THRESHOLD_TIME)) {
                return "지각";
            }
            return "결석";
        }
        if (time.isBefore(PRESENT_THRESHOLD_TIME) || time.equals(PRESENT_THRESHOLD_TIME)) {
            return "출석";
        }
        if (time.isBefore(TARDY_THRESHOLD_TIME) || time.equals(TARDY_THRESHOLD_TIME)) {
            return "지각";
        }
        return "결석";
    }
}
