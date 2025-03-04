package view;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern TIME_PATTERN = Pattern.compile("[0-9]{2}:[0-9]{2}$");
    public static final int HOUR_INDEX = 0;
    public static final int MINUTE_INDEX = 1;

    static void validateTimeFormat(String rawTime) {
        if (!TIME_PATTERN.matcher(rawTime).matches()) {
            throw new IllegalArgumentException("잘못된 시간 포맷입니다.");
        }
    }

    private static void validateHour(String rawTime) {
        String[] splittedTime = rawTime.split(":");
        int hour = Integer.parseInt(splittedTime[HOUR_INDEX]);
        if (hour >= 24 || hour < 0) {
            throw new IllegalArgumentException("시간은 00 ~ 23 사이여야합니다.");
        }
    }

    private static void validateMinute(String rawTime) {
        String[] splittedTime = rawTime.split(":");
        int minute = Integer.parseInt(splittedTime[MINUTE_INDEX]);
        if (minute >= 60 || minute < 0) {
            throw new IllegalArgumentException("분은 00 ~ 59 사이여야합니다.");
        }
    }

    static void validateTime(String rawTime) {
        validateTimeFormat(rawTime);
        validateHour(rawTime);
        validateMinute(rawTime);
    }

    static void validateInteger(String rawInput) {
        try {
            Integer.parseInt(rawInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        }
    }
}
