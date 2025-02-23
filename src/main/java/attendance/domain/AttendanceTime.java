package attendance.domain;

import attendance.utils.Parser;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public record AttendanceTime(LocalDate date, String hour, String minute, boolean isAbsent) {

    private static final int HOUR_MAX = 24;
    private static final int HOUR_MIN = 0;
    private static final int MINUTE_MAX = 60;
    private static final int MINUTE_MIN = 0;

    private static final int CAMPUS_OPEN_HOUR = 8;
    private static final int CAMPUS_CLOSE_HOUR = 8;

    private static final String SATURDAY = "SATURDAY";
    private static final String SUNDAY = "SUNDAY";

    public AttendanceTime {

        if (!isAbsent) {
            validatePossibleTime(date, hour, minute);
            validateInRangeTime(hour, minute);
        }
    }

    private void validateInRangeTime(String hour, String minute) {

        int parsingHour = Parser.parseInt(hour);
        int parsingMinute = Parser.parseInt(minute);
        if (parsingHour >= HOUR_MAX || parsingHour < HOUR_MIN || parsingMinute >= MINUTE_MAX
                || parsingMinute < MINUTE_MIN) {
            throw new IllegalArgumentException("[ERROR] 올바른 시간을 입력해주세요.");
        }
    }

    private void validatePossibleTime(LocalDate date, String hour, String minute) {

        validateAttendanceDate(date);
        validateAttendanceTime(hour, minute);
    }

    private static void validateAttendanceTime(String hour, String minute) {

        int parsingHour = Parser.parseInt(hour);
        int parsingMinute = Parser.parseInt(minute);

        if (parsingHour < CAMPUS_OPEN_HOUR || parsingHour == CAMPUS_CLOSE_HOUR && parsingMinute > MINUTE_MIN) {
            throw new IllegalArgumentException("[ERROR] 출석 가능한 시간이 아닙니다.");
        }
    }

    public static void validateAttendanceDate(LocalDate date) {

        String day = date.getDayOfWeek().name();

        if (day.equals(SATURDAY) || day.equals(SUNDAY)) {
            String message = String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            throw new IllegalArgumentException(message);
        }
    }

    public boolean isAfter(LocalDateTime localDateTime) {

        if (!isAbsent) {
            return date.atTime(Parser.parseInt(hour), Parser.parseInt(minute)).isAfter(localDateTime);
        }

        return true;
    }

    public int getYear() {

        return date.getYear();
    }

    public int getMonth() {

        return date.getMonthValue();
    }

    public int getDay() {

        return date.getDayOfMonth();
    }

    public String getDayOfWeek() {

        return date.getDayOfWeek().getDisplayName(
                TextStyle.FULL, Locale.KOREAN);
    }
}
