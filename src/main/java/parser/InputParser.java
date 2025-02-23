package parser;

import java.time.LocalTime;

public class InputParser {

    private static final String TIME_DELIMITER = ":";
    private static final int TIME_DELIMITER_COUNT = 2;

    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;

    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 60;

    public static LocalTime timeParser(String inputTime) {
        String[] time = inputTime.split(TIME_DELIMITER);
        validateTimeFormat(time);
        int hour = validateRange(time[0], MIN_HOUR, MAX_HOUR);
        int minute = validateRange(time[1], MIN_MINUTE, MAX_MINUTE);

        return LocalTime.of(hour, minute);
    }

    public static int dayParser(String day) {
        return validateRange(day, MIN_DAY, MAX_DAY);
    }

    private static int validateRange(String input, int minRange, int maxRange) {
        int time = validateInteger(input);
        if (time < minRange || time > maxRange) {
            throw new IllegalArgumentException("[ERROR] 범위에 맞게 입력해주세요.");
        }

        return time;
    }

    private static int validateInteger(String time) {
        try {
            return Integer.parseInt(time);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 시간은 정수로 입력해야 합니다.");
        }
    }

    private static void validateTimeFormat(String[] time) {
        if (time.length != TIME_DELIMITER_COUNT) {
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다.");
        }
    }

}
