package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public final class DateTimeFormatterWrapper {

    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";
    private static final String INVALID_ATTENDANCE_DATE = "유효하지 않은 날짜입니다.";
    private static final DateTimeFormatter parsingAttendanceDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter parsingAttendanceTime = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter parsingAttendanceDate = DateTimeFormatter.ofPattern("yyyy MM dd");
    public static final String TODAY_FORMAT = "오늘은 MM월 dd일 E요일입니다. 기능을 선택해 주세요.";

    private static final DateTimeFormatter parsingAttendanceResult = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
            Locale.KOREA);
    public static final String ATTENDANCE_ABSENCE_HISTORY = "MM월 dd일 E요일 --:-- (결석)";
    static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";


    private DateTimeFormatterWrapper() {
        throw new IllegalStateException(INVALID_STATE);
    }

    public static LocalDateTime parsingAttendanceDateTime(String datetime) {
        try {
            return LocalDateTime.parse(datetime, parsingAttendanceDateTime);
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

    public static LocalTime parsingAttendanceTime(String time) {
        try {
            return LocalTime.parse(time, parsingAttendanceTime);
        } catch (DateTimeParseException e) {
            throw new AttendanceArgumentException(INVALID_ATTENDANCE_DATE);
        }
    }

    public static LocalDate parsingAttendanceDate(String date) {
        try {
            return LocalDate.parse(date, parsingAttendanceDate);
        } catch (DateTimeParseException e) {
            throw new AttendanceArgumentException(INVALID_ATTENDANCE_DATE);
        }
    }

    public static String formattingAttendanceDateError(LocalDate currentDate) {
        return currentDate.format(
                DateTimeFormatter.ofPattern(CANNOT_ATTENDANCE_WEEKEND_FORMAT, Locale.KOREA));
    }

    public static String formattingAttendanceAbsenceHistory(LocalDate currentDate) {
        return currentDate.format(
                DateTimeFormatter.ofPattern(ATTENDANCE_ABSENCE_HISTORY, Locale.KOREA));
    }

    public static String formattingToday(LocalDate date) {
        return date.format(
                DateTimeFormatter.ofPattern(TODAY_FORMAT, Locale.KOREA));
    }
}
