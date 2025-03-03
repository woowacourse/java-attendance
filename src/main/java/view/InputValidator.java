package view;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern TIME_FORMAT = Pattern.compile("\\d{1,2}:\\d{1,2}");
    private static final Pattern DATE_FORMAT = Pattern.compile("\\d{1,2}");

    public void validateTimeFormat(String time) {
        if (!TIME_FORMAT.matcher(time).matches()) {
            throw new IllegalArgumentException("시간 형식이 잘못되었습니다(HH:mm)");
        }
    }

    public void validateDateFormat(String date) {
        if (!DATE_FORMAT.matcher(date).matches()) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.(DD)");
        }
    }
}
