package attendance.view;

import attendance.domain.AttendanceChecker;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    private static final String DATE_CONVERT_FORMAT = "%d월 %d일 %s";

    public static void printAddedAttendance(LocalDateTime localDateTime) {
        System.out.println();
        System.out.println(convertAttendanceResult(localDateTime));
    }

    private static String convertAttendanceResult(final LocalDateTime localDateTime) {
        return String.format("%s (%s)", convertDateTime(localDateTime), AttendanceChecker.checkAttendance(localDateTime).getStatus());
    }

    private static String convertDate(LocalDateTime localDateTime) {
        return String.format(DATE_CONVERT_FORMAT, localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
                localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    private static String convertDateTime(LocalDateTime localDateTime) {
        return String.format("%s %s",convertDate(localDateTime), localDateTime.toLocalTime().toString());
    }

}
