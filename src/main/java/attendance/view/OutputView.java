package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
        "MM월 dd일 EEE요일");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private OutputView() {
    }

    public static void printAttendanceResult(Attendance attendance) {
        LocalDateTime attendanceDateTime = attendance.getDateTime();
        AttendanceStatus attendanceStatus = attendance.getStatus();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            DATE_FORMATTER + " " + TIME_FORMATTER);
        String attendanceDate = attendanceDateTime.format(dateTimeFormatter);
        System.out.println(
            System.lineSeparator() + attendanceDate + " (" + attendanceStatus.getStatus() + ")");
    }

    public static void printModifyingResult(Attendance previousAttendance, Attendance attendance) {
        String date = attendance.getDateTime().format(DATE_FORMATTER);
        String beforeTime = previousAttendance.getDateTime().format(TIME_FORMATTER);
        String beforeStatus = previousAttendance.getStatus().getStatus();
        String afterTime = attendance.getDateTime().toLocalTime().format(TIME_FORMATTER);
        String afterStatus = attendance.getStatus().getStatus();
        System.out.printf("%n%s %s (%s) -> %s (%s) 수정 완료!%n",
            date, beforeTime, beforeStatus, afterTime, afterStatus);
    }
}
