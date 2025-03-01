package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {

    public void writeAttendanceRegister(AttendanceTime registerdAttendanceTime) {
        LocalDateTime attendanceTime = registerdAttendanceTime.getAttendanceTime();
        AttendanceStatus attendanceStatus = registerdAttendanceTime.getAttendanceStatus();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm", Locale.KOREA);
        String formattedDate = attendanceTime.format(dateTimeFormatter);

        System.out.println(formattedDate + " (" + attendanceStatus.getName() + ")");
    }

    public void writeErrorMessage(String message) {
        System.out.println(message);
    }
}
