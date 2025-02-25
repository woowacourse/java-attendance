package attendance.common.utill;

public final class StringUtility {
    private static final String WRONG_INPUT_NULL = "[ERROR] 입력값이 Null입니다.";
    private static final String WRONG_INPUT_EMPTY = "[ERROR] 입력값이 공백입니다.";

    private StringUtility() {
    }

    public static void validateIsEmpty(String string) {
        if (string == null) {
            throw new IllegalArgumentException(WRONG_INPUT_NULL);
        }
        if (string.isEmpty() || string.isBlank()) {
            throw new IllegalArgumentException(WRONG_INPUT_EMPTY);
        }
    }
}
