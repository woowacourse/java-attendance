package attendance.utils;

public class Parser {

    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 값을 입력해주세요.");
        }
    }
}
