package attendance.view;

import attendance.domain.AttendanceChecker;
import attendance.domain.HourMinute;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    private static final String ATTENDANCE_RESULT_FORMAT = "%d월 %d일 %s %s (%s)";
    private static final String MODIFY_SUCCESS_FORMAT = "%d월 %d일 %s %s (%s) -> %s (%s) 수정 완료!";

    public static void printAddedAttendance(LocalDateTime localDateTime) {
        System.out.println();
        LocalTime time = localDateTime.toLocalTime();
        System.out.printf(ATTENDANCE_RESULT_FORMAT,
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                time.toString(),
                AttendanceChecker.checkAttendance(localDateTime).getStatus());
    }

    private static String convertAttendanceResult(final LocalDateTime localDateTime) {
        return String.format("%s (%s)", convertDateTime(localDateTime),
                AttendanceChecker.checkAttendance(localDateTime).getStatus());
    }

    private static String convertDate(LocalDateTime localDateTime) {
        return String.format("%d %d %s", localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    private static String convertDateTime(LocalDateTime localDateTime) {
        return String.format("%s %s", convertDate(localDateTime), localDateTime.toLocalTime().toString());
    }

    public static void printModifiedAttendance(HourMinute prevHourMinute, LocalDateTime newAttendanceTime) {
        LocalTime prevTime = LocalTime.of(prevHourMinute.hour(), prevHourMinute.minute());
        System.out.printf(MODIFY_SUCCESS_FORMAT,
                newAttendanceTime.getMonthValue(),
                newAttendanceTime.getDayOfMonth(),
                newAttendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                prevTime.toString(),
                prevHourMinute.attendanceStatus().getStatus(),
                newAttendanceTime.toLocalTime().toString(),
                AttendanceChecker.checkAttendance(newAttendanceTime).getStatus());
    }

}
