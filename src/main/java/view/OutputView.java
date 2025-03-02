package view;

import controller.DateTimeConverter;
import domain.attendance.AttendanceStatus;
import java.time.LocalDateTime;

public class OutputView {
    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAttend(LocalDateTime attendedTime, String attendanceStatus) {
        System.out.println(DateTimeConverter.convertLocalDateToString(attendedTime.toLocalDate()) + " "
                + DateTimeConverter.convertLocalTimeToString(attendedTime.toLocalTime())
                + " (" + attendanceStatus + ")");
    }

    public void printEdit(LocalDateTime beforeDateTime,
                          String beforeStatus,
                          LocalDateTime afterDateTime,
                          String afterStatus) {
        System.out.println(DateTimeConverter.convertLocalDateToString(beforeDateTime.toLocalDate()) + " "
                + DateTimeConverter.convertLocalTimeToString(beforeDateTime.toLocalTime())
                + " (" + beforeStatus + ") -> "
                + DateTimeConverter.convertLocalTimeToString(afterDateTime.toLocalTime())
                + " (" + afterStatus + ") 수정 완료!");
    }
}
