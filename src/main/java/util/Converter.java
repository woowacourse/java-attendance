package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Converter {

    private Converter() {

    }

    public static LocalTime convertStringToLocalTime(String timeInput) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            return LocalTime.parse(timeInput, dateTimeFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력된 시간 형식이 적절하지 않습니다.");
        }
    }

    public static String covertLocalTimeToString(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return localTime.format(formatter);
    }
}
