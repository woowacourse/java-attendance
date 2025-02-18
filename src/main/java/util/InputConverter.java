package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InputConverter {

    private InputConverter() {

    }

    public static LocalTime convertToLocalTime(String timeInput) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            return LocalTime.parse(timeInput, dateTimeFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력된 시간 형식이 적절하지 않습니다.");
        }
    }
}
