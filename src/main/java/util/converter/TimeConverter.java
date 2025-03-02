package util.converter;

import exception.ErrorException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeConverter {

    private TimeConverter() {
    }

    public static LocalTime convertStringToTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new ErrorException("올바른 시간 형식이 아닙니다. 입력 값 : " + time);
        }
    }
}
