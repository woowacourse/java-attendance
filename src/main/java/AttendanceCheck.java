import domain.DayOfWeek;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceCheck {

    public static String checkAttendanceStatus(LocalTime standardTime, LocalTime localTime) {
        long betweenMinutes = Duration.between(standardTime, localTime).toMinutes();
        if (betweenMinutes <= 5) {
            return "출석";
        }

        if (betweenMinutes > 5 && betweenMinutes <= 30) {
            return "지각";
        }

        return "결석";
    }

    public static String convertKorean(LocalDate localDate) {
        return DayOfWeek.getNameById(localDate.getDayOfWeek().getValue());
    }

    public static LocalTime getStandardTime(LocalDate localDate) {
        try {
            return DayOfWeek.getStandardTimeById(localDate.getDayOfWeek().getValue());
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 오늘은 등교일이 아닙니다.");
        }
    }
}
