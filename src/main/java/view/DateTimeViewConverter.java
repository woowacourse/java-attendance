package view;

import static domain.AttendanceHistory.ABSENT_DEFAULT_HOUR;
import static domain.AttendanceHistory.ABSENT_DEFAULT_MINUTE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeViewConverter {
    public final static int STANDARD_YEAR = 2024;
    public final static int STANDARD_MONTH = 12;
    private final static String DELIMITER = ":";
    private final static int HOUR_INDEX = 0;
    private final static int MINUTES_INDEX = 1;
    private final static String TIME_FORMAT = "^(?:[01]\\d|2[0-3])\\s*:\\s*[0-5]\\d$";

    private static final DateTimeFormatter NORMAL_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
    private static final DateTimeFormatter DATE_ONLY_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);

    public static LocalDateTime changeToDate(String input) {
        validateDate(input.trim());
        String[] words = input.split(DELIMITER);
        int hour = Integer.parseInt(words[HOUR_INDEX].trim());
        int minutes = Integer.parseInt(words[MINUTES_INDEX].trim());
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, now.getDayOfMonth(), hour, minutes);
    }

    public static String dateFormattingForOutput(LocalDateTime localDateTime) {
        if (localDateTime.getHour() == ABSENT_DEFAULT_HOUR && localDateTime.getMinute() == ABSENT_DEFAULT_MINUTE) {
            return localDateTime.format(DATE_ONLY_FORMATTER) + " --:--";
        }
        return localDateTime.format(NORMAL_FORMATTER);
    }

    public static String dateFormattingForInput(LocalDateTime localDateTime) {
        LocalDateTime changedDate = changeStandardDate(localDateTime);
        return changedDate.format(DATE_ONLY_FORMATTER);
    }

    public static String timeFormattingForOutput(LocalDateTime localDateTime) {
        return localDateTime.format(TIME_FORMATTER);
    }

    public static LocalDateTime editDayOfMonth(String date, String time) {
        validateDate(time.trim());
        validateNumber(date.trim());
        String[] words = time.split(DELIMITER);
        int hour = Integer.parseInt(words[HOUR_INDEX].trim());
        int minutes = Integer.parseInt(words[MINUTES_INDEX].trim());
        int day = Integer.parseInt(date);
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, day, hour, minutes);
    }

    public static LocalDateTime changeStandardDate(LocalDateTime time) {
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, time.getDayOfMonth(), time.getHour(), time.getMinute());
    }


    public static void validateDate(String input) {
        if (!input.matches(TIME_FORMAT)) {
            throw new IllegalArgumentException("[ERROR] 00:00 형식으로 입력해주세요.");
        }
    }

    public static void validateNumber(String input) {
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("[ERROR]  숫자를 입력해주세요.");
        }
    }
}
