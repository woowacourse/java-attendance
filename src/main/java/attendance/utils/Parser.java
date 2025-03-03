package attendance.utils;

public class Parser {

    private Parser() {

    }

    public static int parseToInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 입력입니다.");
        }
    }
}
