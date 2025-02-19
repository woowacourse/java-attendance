package attendance.utils;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceChecker {

    public static String check(LocalDateTime dateTime) {
        String day = dateTime.getDayOfWeek().name();

        if (day.equals("SATURDAY") || day.equals("SUNDAY")) {
            String message = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                    dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            throw new IllegalArgumentException(message);
        }

        int hour = dateTime.getHour();
        if (hour < 8 || (hour == 23 && dateTime.getMinute() > 0)) {
            throw new IllegalArgumentException("[ERROR] 출석 가능한 시간이 아닙니다.");
        }

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
