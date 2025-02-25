package attendance.common.utill;

public final class IntegerUtility {
    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";
    private static final String ERROR_PARSE = "[ERROR] 날짜는 숫자로만 입력해야 합니다: ";

    private IntegerUtility() {
        throw new AssertionError(INVALID_STATE);
    }

    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_PARSE + input);
        }
    }
}
