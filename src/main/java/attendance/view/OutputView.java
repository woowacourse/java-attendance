package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();
        AttendanceStatus status = attendance.getStatus();

        System.out.printf("%d월 %2d일 %s %02d:%02d (%s)\n", dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                status.getMessage());
        //12월 05일 화요일 09:59 (출석)
    }
}
