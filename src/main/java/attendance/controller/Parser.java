package attendance.controller;

import static attendance.error.ErrorMessage.ERROR_NOT_NUMERIC;

public class Parser {

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(ERROR_NOT_NUMERIC);
        }
    }
}
