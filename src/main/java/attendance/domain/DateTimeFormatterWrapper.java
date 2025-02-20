package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import attendance.exception.AttendanceArgumentException;

public final class DateTimeFormatterWrapper {

    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";
    private static final String INVALID_ATTENDANCE_DATE = "유효하지 않은 날짜 양식입니다.";
    private static final DateTimeFormatter parsingAttendanceDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter parsingAttendanceResult = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
        Locale.KOREA);
    private static final DateTimeFormatter parsingAttendanceTime = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeFormatterWrapper() {
        throw new IllegalStateException(INVALID_STATE);
    }

    public static LocalDateTime parsingAttendanceDate(String datetime) {
        try {
            return LocalDateTime.parse(datetime, parsingAttendanceDate);
        } catch (DateTimeParseException e) {
            throw new AttendanceArgumentException(INVALID_ATTENDANCE_DATE);
        }
    }

    public static String parsingAttendanceResult(LocalDateTime datetime) {
        try {
            return parsingAttendanceResult.format(datetime);
        } catch (DateTimeParseException e) {
            throw new AttendanceArgumentException(INVALID_ATTENDANCE_DATE);
        }
    }

    public static String parsingAttendanceTime(LocalTime time) {
        try {
            return parsingAttendanceTime.format(time);
        } catch (DateTimeParseException e) {
            throw new AttendanceArgumentException(INVALID_ATTENDANCE_DATE);
        }
    }

    public static String formattingAttendanceWeekendError(LocalDate currentDate) {
        return currentDate.format(
            DateTimeFormatter.ofPattern(AttendanceManagerHelper.CANNOT_ATTENDANCE_WEEKEND_FORMAT, Locale.KOREA));
    }

    public static String formattingAttendanceAbsenceHistory(LocalDate currentDate) {
        return currentDate.format(
            DateTimeFormatter.ofPattern(AttendanceManagerHelper.ATTENDANCE_ABSENCE_HISTORY, Locale.KOREA));
    }
}
