package attendance.controller;

public class Parser {

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        }
    }
}
