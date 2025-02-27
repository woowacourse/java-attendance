import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTimeChecker {

    private static final int CRITERION_HOUR_FOR_MONDAY = 13;
    private static final int CRITERION_HOUR_EXCLUDE_MONDAY = 10;
    private static final int CRITERION_MINUTE_FOR_LATE = 5;
    private static final int CRITERION_MINUTE_FOR_ABSENT = 30;

    public AttendPolicy attendanceCheck(LocalDate date, LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (hour < 8) {
            throw new IllegalArgumentException();
        }

        if (hour == 23 && minute > 0) {
            throw new IllegalArgumentException();
        }

        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return determineAttendPolicy(hour, minute, CRITERION_HOUR_FOR_MONDAY);
        }

        return determineAttendPolicy(hour, minute, CRITERION_HOUR_EXCLUDE_MONDAY);
    }

    private AttendPolicy determineAttendPolicy(int hour, int minute, int criterionHour) {
        if (hour > criterionHour) {
            return AttendPolicy.ABSENT;
        }

        if (hour < criterionHour) {
            return AttendPolicy.ATTEND;
        }

        if (minute > CRITERION_MINUTE_FOR_ABSENT) {
            return AttendPolicy.ABSENT;
        }

        if (minute > CRITERION_MINUTE_FOR_LATE) {
            return AttendPolicy.LATE;
        }

        return AttendPolicy.ATTEND;
    }
}
