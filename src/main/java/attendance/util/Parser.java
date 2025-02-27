package attendance.util;

import static attendance.error.ErrorMessage.ERROR_NOT_NUMERIC;

public class Parser {

    public static int parseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_NOT_NUMERIC);
        }
    }

}
