package attendance.util;

public final class Parser {

    private Parser() {
    }

    public static int parseInt(final String input) {
        
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 숫자를 입력해 주세요.");
        }
    }
}
