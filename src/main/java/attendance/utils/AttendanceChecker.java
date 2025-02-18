package attendance.utils;

import java.time.LocalDateTime;

public class AttendanceChecker {

    public static String check(LocalDateTime dateTime) {
        // 평일로 가정

        String day = dateTime.getDayOfWeek().name();
        if (day.equals("MONDAY")) { // 월요일

            return checkStatusWithCondition(dateTime, 13, 0, 6);
        }

        return checkStatusWithCondition(dateTime, 10, 0, 6);
    }

    private static String checkStatusWithCondition(LocalDateTime dateTime, int hour, int attendanceMinute,
                                                   int lateMinute) {

        if (!dateTime.isAfter(
                LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), hour,
                        attendanceMinute))) {
            return "출석";
        }

        if (!dateTime.isAfter(
                LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), hour,
                        lateMinute))) {
            return "지각";
        }

        return "결석";
    }
}
