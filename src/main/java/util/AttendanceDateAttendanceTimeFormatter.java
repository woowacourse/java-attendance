package util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import model.AttendanceDate;
import model.AttendanceStatus;
import model.AttendanceTime;

public class AttendanceDateAttendanceTimeFormatter {

    private static final String DATE_PATTERN = "MM월 dd일 EEEE";
    private static final String TIME_PATTERN = "HH:mm";
    private static final String EMPTY_TIME_PATTERN = " --:--";
    private static final String ERROR = "[ERROR]";
    private static final String SPACE = " ";
    private static final String TODAY = "오늘은 ";
    private static final String SELECT_MENU = " 기능을 선택해 주세요.";
    private static final String SENTENCE_ENDING = "입니다. ";

    private static final DateTimeFormatter KOREAN_DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.KOREAN);
    private static final DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static String createAttendanceResultMessage(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        if (attendanceTime.isMissingAttendanceTime()) {
            return attendanceDate.toLocalDate().format(KOREAN_DATE_FORMATTER) + EMPTY_TIME_PATTERN;
        }
        return attendanceDate.toLocalDate().format(KOREAN_DATE_FORMATTER) + SPACE + attendanceTime.toLocalTime().format(HOUR_MINUTE);
    }

    public static String createNonSchoolDayMessage(AttendanceDate attendanceDate) {
        return ERROR + attendanceDate.toLocalDate().format(KOREAN_DATE_FORMATTER) + "은 등교일이 아닙니다.";
    }

    public static String createTodayInformation(AttendanceDate attendanceDate) {
        return TODAY + attendanceDate.toLocalDate().format(KOREAN_DATE_FORMATTER) + SENTENCE_ENDING + SELECT_MENU;
    }

    public static String createModifyCompleteMessage(AttendanceTime attendanceTime, AttendanceStatus attendanceStatus) {
        return attendanceTime.toLocalTime().format(HOUR_MINUTE) + " (" + attendanceStatus.getStatus() + ") 수정 완료!";
    }
}
