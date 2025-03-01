package attendance.view;

import attendance.domain.Attendance;

import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private OutputView() {}

    public static void printAttendanceResult(Attendance attendance) {
        System.out.printf("%n%d월 %d일 %s %02d:%02d (%s)%n%n",
                attendance.getAttendDate().getMonthValue(),
                attendance.getAttendDate().getDayOfMonth(),
                attendance.getAttendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.getAttendTime().getHour(),
                attendance.getAttendTime().getMinute(),
                attendance.determineStatus().getName());
    }
}
