package attendance.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ResultView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);

    public void printErrorMessage(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message));
    }

    public void printAttendanceConfirmResult(final LocalDateTime attendanceDateTime, final String attendanceStatus) {
        System.out.println();
        System.out.printf(String.join(" ", DATE_TIME_FORMATTER.format(attendanceDateTime), "(%s)"), attendanceStatus);
        System.out.println();
    }

}
