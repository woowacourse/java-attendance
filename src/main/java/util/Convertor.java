package util;

import static domain.AttendanceHistory.ABSENT_DEFAULT_HOUR;
import static domain.AttendanceHistory.ABSENT_DEFAULT_MINUTE;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class Convertor {
    private final static String DELIMITER = ":";
    private final static int STANDARD_YEAR = 2024;
    private final static int STANDARD_MONTH = 12;
    private final static int HOUR_INDEX = 0;
    private final static int MINUTES_INDEX = 1;

    public static LocalDateTime changeToDate(String input) {
        Validator.validateDate(input.trim());
        String[] words = input.split(DELIMITER);
        int hour = Integer.parseInt(words[HOUR_INDEX].trim());
        int minutes = Integer.parseInt(words[MINUTES_INDEX].trim());
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, now.getDayOfMonth(), hour, minutes);
    }

    public static String dateFormattingForOutput(LocalDateTime localDateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
        if ((localDateTime.getHour() == ABSENT_DEFAULT_HOUR) & (localDateTime.getMinute() == ABSENT_DEFAULT_MINUTE)) {
            dateFormat = new SimpleDateFormat("MM월 dd일 E요일 --:--", Locale.KOREAN);
        }
        Date dateAttendanceTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(dateAttendanceTime);

    }

    public static String dateFormattingForInput(LocalDateTime localDateTime) {
        LocalDateTime changedDate = changeStandardDate(localDateTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        Date dateAttendanceTime = Date.from(changedDate.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(dateAttendanceTime);
    }

    public static String timeFormattingForOutput(LocalDateTime localDateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.KOREAN);
        Date dateAttendanceTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(dateAttendanceTime);
    }

    public static LocalDateTime editDayOfMonth(String date, String time) {
        Validator.validateDate(time.trim());
        Validator.validateNumber(date.trim());
        String[] words = time.split(DELIMITER);
        int hour = Integer.parseInt(words[HOUR_INDEX].trim());
        int minutes = Integer.parseInt(words[MINUTES_INDEX].trim());
        int day = Integer.parseInt(date);
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, day, hour, minutes);
    }

    public static LocalDateTime changeStandardDate(LocalDateTime time) {
        return LocalDateTime.of(STANDARD_YEAR, STANDARD_MONTH, time.getDayOfMonth(), time.getHour(), time.getMinute());
    }


    static class Validator {
        private final static String TIME_FORMAT = "^(?:[01]\\d|2[0-3])\\s*:\\s*[0-5]\\d$";

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


}
