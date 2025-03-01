package attendance.view;

import attendance.domain.Attendance;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    private static final String ATTENDANCE_DESCRIPTION_FORMAT = "%02d월 %02d일 %s %s (%s)\n";

    public OutputView() {
    }

    public void printCheckAttendanceResult(Attendance attendance) {
        System.out.print(createAttendanceDescription(LocalDate.now(), attendance));
    }

    private String createAttendanceDescription(LocalDate date, Attendance attendance) {
        return String.format(ATTENDANCE_DESCRIPTION_FORMAT,
                date.getMonthValue(),
                date.getDayOfMonth(),
                getDisplayName(date),
                attendance.status().getStatus()
        );
    }

    public void printErrorMessage(Exception error) {
        System.out.println(ERROR_MESSAGE_PREFIX + error);
    }

    private static String getDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
    }
}
