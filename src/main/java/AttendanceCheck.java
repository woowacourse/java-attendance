import java.time.Duration;
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
}
