package attendance.common.utill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import attendance.common.exception.AttendanceArgumentException;
import attendance.domain.AttendanceManagerHelper;

public final class DateTimeFormatterWrapper {

    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";
    private static final String INVALID_ATTENDANCE_DATE = "유효하지 않은 날짜입니다.";
    private static final DateTimeFormatter parsingAttendanceDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter parsingAttendanceResult = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
        Locale.KOREA);
    private static final DateTimeFormatter parsingAttendanceTime = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter parsingAttendanceDate = DateTimeFormatter.ofPattern("yyyy MM dd");

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

    // public static String formattingAttendanceWeekendError(LocalDate currentDate) {
    //     return currentDate.format(
    //         DateTimeFormatter.ofPattern(AttendanceManagerHelper.CANNOT_ATTENDANCE_WEEKEND_FORMAT, Locale.KOREA));
    // }

    public static String formattingAttendanceAbsenceHistory(LocalDate currentDate) {
        return currentDate.format(
            DateTimeFormatter.ofPattern(AttendanceManagerHelper.ATTENDANCE_ABSENCE_HISTORY, Locale.KOREA));
    }

    public static String formattingToday(LocalDate date) {
        return date.format(
            DateTimeFormatter.ofPattern(AttendanceManagerHelper.TODAY_FORMAT, Locale.KOREA));
    }
}
