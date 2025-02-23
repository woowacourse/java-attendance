package attendance.common.utill;

public class StringUtility {

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
