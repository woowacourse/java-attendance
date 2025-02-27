package attendance.utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public final class DateTimeParser {
    private static final String ERROR_PARSE_DATE = "[ERROR] 날짜는 숫자로만 입력해야 합니다: ";
    private static final String ERROR_PARSE_TIME = "[ERROR] 시간 입력은 --:--와 같은 형태야합니다: ";

    private DateTimeParser() {
    }

    public static LocalTime parseToTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_PARSE_DATE + input);
        }
    }

    public static LocalDate parseToDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_PARSE_TIME + input);
        }
    }
}
