import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceTimeChecker {

    private static final int CRITERION_HOUR_FOR_MONDAY = 13;
    private static final int CRITERION_HOUR_EXCLUDE_MONDAY = 10;
    private static final int CRITERION_MINUTE_FOR_LATE = 5;
    private static final int CRITERION_MINUTE_FOR_ABSENT = 30;

    public AttendPolicy attendanceCheck(LocalDate date, LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        validateInWeekDays(date);
        validateInOperatingTime(hour, minute);

        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return determineAttendPolicy(hour, minute, CRITERION_HOUR_FOR_MONDAY);
        }

        return determineAttendPolicy(hour, minute, CRITERION_HOUR_EXCLUDE_MONDAY);
    }

    private void validateInWeekDays(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
        || date.isEqual(LocalDate.of(2024, 12, 25))) {
            String koreanDayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
            throw new IllegalArgumentException(String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                    date.getMonth().getValue(), date.getDayOfMonth(), koreanDayOfWeek));
        }
    }

    private void validateInOperatingTime(int hour, int minute) {
        if (hour < 8 || (hour == 23 && minute > 0)) {
            throw new IllegalArgumentException("[ERROR] 운영 시간이 아닙니다.");
        }
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
