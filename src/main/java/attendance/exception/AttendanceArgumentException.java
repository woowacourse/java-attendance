package attendance.exception;

import java.time.DateTimeException;
import java.time.LocalDate;

import attendance.utility.DateTimeFormatterWrapper;

public class AttendanceArgumentException extends IllegalArgumentException {

    public AttendanceArgumentException(String message) {
        super("[ERROR] " + message);
    }

    public AttendanceArgumentException(String message, LocalDate date) {
        super(formatErrorMessage(message, date));
    }

    private static String formatErrorMessage(String pattern, LocalDate date) {
        try {
            var formatter = DateTimeFormatterWrapper.getFormatter(pattern);
            return "[ERROR] " + date.format(formatter);
        } catch (DateTimeException e) {
            return "[ERROR] 잘못된 날짜 형식입니다 : " + pattern;
        }
    }
}
