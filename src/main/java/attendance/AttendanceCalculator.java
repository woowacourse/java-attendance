package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceCalculator {

    public static String findDayOfWeek(LocalDate currentDate) {
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREA);
    }

    public static String decideAttendanceType(String dayOfWeek, LocalTime attendanceTime) {
        if (dayOfWeek.equals("월요일")) {
            return "출석";
        }
        if (attendanceTime.isAfter(LocalTime.of(10, 29))) {
            return "결석";
        }
        if (attendanceTime.isAfter(LocalTime.of(10, 4))) {
            return "지각";
        }
        return "출석";
    }
}
