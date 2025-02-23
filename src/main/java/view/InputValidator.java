package view;

public class InputValidator {

    public void validateTimeFormat(String input) {
        String timeFormatRegexp = "[0-9][0-9]:[0-9][0-9]";
        if (!input.matches(timeFormatRegexp)) {
            throw new IllegalArgumentException("시간 형식이 잘못되었습니다.");
        }
    }

    public void validateInteger(String input) {
        String timeFormatRegexp = "[0-9]{1,2}";
        if (!input.matches(timeFormatRegexp)) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }
}
