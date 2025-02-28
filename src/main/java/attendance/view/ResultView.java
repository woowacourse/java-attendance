package attendance.view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ResultView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);
    private static final DateTimeFormatter DATE_FORMATTER_WITHOUT_TIME =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--", Locale.KOREA);
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);

    public void printErrorMessage(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message));
    }

    public void printAttendanceConfirmResult(final LocalDateTime attendanceDateTime, final String attendanceStatus) {
        System.out.println();
        System.out.printf(String.join(" ", DATE_TIME_FORMATTER.format(attendanceDateTime), "(%s)"), attendanceStatus);
        System.out.println();
    }

    public void printOriginAttendanceRecord(
            final boolean hasOriginAttendance, final LocalDateTime originAttendanceDateTime,
            final String originAttendanceStatus
    ) {
        System.out.println();
        if (hasOriginAttendance) {
            System.out.printf(String.join(" ",
                    DATE_TIME_FORMATTER.format(originAttendanceDateTime),
                    "(%s)"), originAttendanceStatus
            );
            return;
        }
        System.out.printf(String.join(" ",
                DATE_FORMATTER_WITHOUT_TIME.format(originAttendanceDateTime),
                "(%s)"), originAttendanceStatus
        );
    }

    public void printModificationAttendanceRecord(
            final LocalTime modificationAttendanceTime, final String modificationAttendanceStatus
    ) {
        System.out.printf(String.join(
                "", " -> ", TIME_FORMATTER.format(modificationAttendanceTime),
                " (%s)%n"), modificationAttendanceStatus);
        System.out.println();
    }

}
