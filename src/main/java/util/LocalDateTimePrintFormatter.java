package util;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceDateTime;

public class LocalDateTimePrintFormatter {

    private static final String DATE_PATTERN = "MM월 dd일 EEEE";
    private static final String TIME_PATTERN = "HH:mm";
    private static final String FULL_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;
    private static final String EMPTY_TIME_PATTERN = DATE_PATTERN + " --:--";

    private static final DateTimeFormatter KOREAN_DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.KOREAN);
    private static final DateTimeFormatter KOREAN_FULL_FORMATTER = DateTimeFormatter.ofPattern(FULL_PATTERN, Locale.KOREAN);
    private static final DateTimeFormatter KOREAN_EMPTY_TIME_FORMATTER = DateTimeFormatter.ofPattern(EMPTY_TIME_PATTERN, Locale.KOREAN);
    private static final DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static String createAttendanceResultMessage(AttendanceDateTime attendanceDateTime) {
        if (attendanceDateTime.isZeroTime(HOUR_MINUTE)) {
            return attendanceDateTime.toLocalDate().format(KOREAN_EMPTY_TIME_FORMATTER);
        }
        return attendanceDateTime.getAttendanceDateTime().format(KOREAN_FULL_FORMATTER);
    }

    public static String createNonSchoolDayMessage(AttendanceDateTime attendanceDateTime) {
        return "[ERROR] " + attendanceDateTime.toLocalDate().format(KOREAN_DATE_FORMATTER) + "은 등교일이 아닙니다.";
    }

    public static String createModifyCompleteMessage(AttendanceDateTime attendanceDateTime, String recordAfterModifyState) {
        return attendanceDateTime.getAttendanceDateTime().format(HOUR_MINUTE) + " (" + recordAfterModifyState + ") 수정 완료!";
    }
}
