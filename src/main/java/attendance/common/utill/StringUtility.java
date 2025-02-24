package attendance.common.utill;

public final class StringUtility {
    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";

    private StringUtility() {
        throw new AssertionError(INVALID_STATE);
    }

    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        if (string.isEmpty() || string.isBlank()) {
            return true;
        }
        return false;
    }
}
