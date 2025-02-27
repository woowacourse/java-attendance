package util;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceDateTime;

public class LocalDateTimePrintFormatter {

    public static final DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern("HH:mm");

    public static String createAttendanceResultMessage(AttendanceDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = attendanceDateTime.toDayOfWeek();

        if (attendanceDateTime.isZeroTime(HOUR_MINUTE)) {
            return attendanceDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.KOREAN) + " --:--"));
        }

        return attendanceDateTime.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                TextStyle.FULL, Locale.KOREAN) + " HH:mm"));
    }

    public static String createNonSchoolDayMessage(AttendanceDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = attendanceDateTime.toDayOfWeek();

        String formattedDate = attendanceDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("MM월 dd일"));

        return "[ERROR] " + formattedDate + " " +
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN) +
                "은 등교일이 아닙니다.";
    }

    public static String creatModifyCompleteMessage(AttendanceDateTime attendanceDateTime, String recordAfterModifyState) {
        return attendanceDateTime.getAttendanceDateTime().format(HOUR_MINUTE) + " (" +recordAfterModifyState + ") 수정 완료!";
    }


}
