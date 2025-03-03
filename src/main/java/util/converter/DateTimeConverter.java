package util.converter;

import exception.ErrorException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConverter {

    private DateTimeConverter() {
    }

    public static LocalDateTime convertStringToDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ErrorException("올바른 날짜와 시간 형식이 아닙니다. 입력 값 : " +  dateTime);
        }
    }
}
