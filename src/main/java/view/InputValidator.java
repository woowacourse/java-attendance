package view;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern TIME_FORMAT_PATTERN = Pattern.compile("[0-9][0-9]:[0-9][0-9]");
    private static final Pattern DAY_FORMAT_PATTERN = Pattern.compile("[0-9]{1,2}");

    public void validateTimeFormat(String input) {
        if (!TIME_FORMAT_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("시간 형식이 잘못되었습니다.");
        }
    }

    public void validateInteger(String input) {
        if (!DAY_FORMAT_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }
}
