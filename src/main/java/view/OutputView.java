package view;

import domain.AttendanceStatus;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(LocalDateTime attendanceTime,
                                      AttendanceStatus attendanceStatus) {
        System.out.println();
        System.out.printf("%d월 %02d일 %s %02d:%02d (%s)",
                attendanceTime.getMonthValue(),
                attendanceTime.getDayOfMonth(),
                attendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendanceTime.getHour(),
                attendanceTime.getMinute(),
                attendanceStatus.getStatus()
        );
        System.out.println();
        System.out.println();
    }

    public void printErrorMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }
}
