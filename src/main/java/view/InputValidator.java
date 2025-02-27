package view;

import static util.Constants.ERROR_HEADER;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final String TIME_FORMAT_ERROR = "시간 형식(HH:mm)이 올바르지 않습니다.";
    private static final String NAME_FORMAT_ERROR = "닉네임은 2자 이상 4자 이하로 입력해주세요.";

    private static final String VALID_TIME_FORMAT = "HH:mm";
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 4;

    public static void validateTime(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(VALID_TIME_FORMAT);
        try {
            LocalTime.parse(time, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_HEADER + TIME_FORMAT_ERROR);
        }
    }

    public static void validateName(String name) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ERROR_HEADER + NAME_FORMAT_ERROR);
        }
    }
}
