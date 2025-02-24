package attendance.utils;

public final class Parser {
    private Parser() {
    }

    public static int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 값을 입력해주세요.");
        }
    }
}
