package attendance.common.utill;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateTimeFormatterWrapper {

    private static final String INVALID_STATE = "유효하지 않은 접근입니다.";
    private static final String INVALID_ATTENDANCE_DATE = "유효하지 않은 날짜입니다.";
    private static final DateTimeFormatter parsingAttendanceDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter parsingAttendanceResult = DateTimeFormatter.ofPattern("MM월 dd일 E요일",
        Locale.KOREA);
    private static final DateTimeFormatter parsingAttendanceTime = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter parsingAttendanceDate = DateTimeFormatter.ofPattern("yyyy MM dd");

    private DateTimeFormatterWrapper() {
        throw new AssertionError(INVALID_STATE);
    }

    public static DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern)
            .withLocale(Locale.KOREAN);
    }
}
