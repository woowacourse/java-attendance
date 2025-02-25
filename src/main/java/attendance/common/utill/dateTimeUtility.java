package attendance.common.utill;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public final class dateTimeUtility {
    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";
    private static final String ERROR_PARSE = "[ERROR] 날짜는 숫자로만 입력해야 합니다: ";

    private dateTimeUtility() {
        throw new AssertionError(INVALID_STATE);
    }

    public static LocalTime parseToTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_PARSE + input);
        }
    }

    public static LocalDate parseToDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_PARSE + input);
        }
    }
}
