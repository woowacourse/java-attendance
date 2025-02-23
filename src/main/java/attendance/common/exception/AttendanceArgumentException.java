package attendance.common.exception;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import attendance.common.utill.DateTimeFormatterWrapper;

public class AttendanceArgumentException extends IllegalArgumentException {

    public AttendanceArgumentException(String message) {
        super("[ERROR] " + message);
    }

    public AttendanceArgumentException(String message, LocalDateTime localDateTime) {
        super(formatErrorMessage(message, localDateTime));
    }

    private static String formatErrorMessage(String pattern, LocalDateTime dateTime) {
        try {
            var formatter = DateTimeFormatterWrapper.getFormatter(pattern);
            return "[ERROR] " + dateTime.format(formatter);
        } catch (DateTimeException e) {
            return "[ERROR] 잘못된 날짜 형식입니다 : " + pattern;
        }
    }
}
