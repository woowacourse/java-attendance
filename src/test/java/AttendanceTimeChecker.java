import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTimeChecker {
    public AttendPolicy attendanceCheck(LocalDate date, LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            if (minute > 5) {
                return AttendPolicy.LATE;
            }
        }

        if (hour > 10) {
            return AttendPolicy.ABSENT;
        }

        if (hour < 10) {
            return AttendPolicy.ATTEND;
        }

        if (minute > 30) {
            return AttendPolicy.ABSENT;
        }

        if (minute > 5) {
            return AttendPolicy.LATE;
        }

        return AttendPolicy.ATTEND;
    }
}
