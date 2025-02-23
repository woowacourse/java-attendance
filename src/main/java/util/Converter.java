package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Converter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private Converter() {
    }

    public static LocalTime convertStringToLocalTime(String timeInput) {
        try {
            return LocalTime.parse(timeInput, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력된 시간 형식이 적절하지 않습니다.");
        }
    }

    public static String covertLocalTimeToString(LocalTime localTime) {

        if (localTime == null) {
            return "--:--";
        }

        return localTime.format(formatter);
    }

    public static Integer convertStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }
}
