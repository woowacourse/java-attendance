package attendance.domain;

import attendance.utils.Parser;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public record Time(LocalDate date, String hour, String minute, boolean isAbsent) {
    public Time {
        if (!isAbsent) {
            validatePossibleTime(date, hour, minute);
            validateInRangeTime(hour, minute);
        }
    }

    private void validateInRangeTime(String hour, String minute) {
        int parsingHour = Parser.parseInt(hour);
        int parsingMinute = Parser.parseInt(minute);
        if (parsingHour >= 24 || parsingHour < 0 || parsingMinute >= 60
                || parsingMinute < 0) {
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

        if (parsingHour < 8 || parsingHour == 23 && parsingMinute > 0) {
            throw new IllegalArgumentException("[ERROR] 출석 가능한 시간이 아닙니다.");
        }
    }

    private static void validateAttendanceDate(LocalDate date) {
        String day = date.getDayOfWeek().name();

        if (day.equals("SATURDAY") || day.equals("SUNDAY")) {
            String message = String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            throw new IllegalArgumentException(message);
        }
    }

    public boolean isBefore(LocalDateTime localDateTime) {
        if (!isAbsent) {
            return !date.atTime(Parser.parseInt(hour), Parser.parseInt(minute)).isAfter(localDateTime);
        }

        return false;
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
