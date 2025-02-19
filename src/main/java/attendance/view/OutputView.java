package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 HH:mm");

    private OutputView() {}

    public static void printAttendanceResult(Attendance attendance) {
        LocalDateTime attendanceDateTime = attendance.getDateTime();
        AttendanceStatus attendanceStatus = attendance.getStatus();
        String attendanceDate = attendanceDateTime.format(FORMATTER);
        System.out.println(System.lineSeparator() + attendanceDate + " (" + attendanceStatus.getStatus() + ")");
    }
}
