package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm", Locale.KOREA);

    public void writeAttendanceRegister(AttendanceTime registerdAttendanceTime) {
        LocalDateTime attendanceTime = registerdAttendanceTime.getAttendanceTime();
        AttendanceStatus attendanceStatus = registerdAttendanceTime.getAttendanceStatus();
        String formattedDate = attendanceTime.format(format);

        System.out.println(formattedDate + " (" + attendanceStatus.getName() + ")");
    }

    public void writeErrorMessage(String message) {
        System.out.println(message);
    }

    public void writeAttendanceModify(AttendanceTime beforeTime, AttendanceTime updateTime) {
        AttendanceStatus beforeAttendanceStatus = beforeTime.getAttendanceStatus();
        AttendanceStatus updateAttendanceStatus = updateTime.getAttendanceStatus();
        LocalDateTime beforeAttendanceTime = beforeTime.getAttendanceTime();
        LocalDateTime updateAttendanceTime = updateTime.getAttendanceTime();
        String beforeFormat = beforeAttendanceTime.format(format);
        String updateFormat = updateAttendanceTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println(beforeFormat + " (" + beforeAttendanceStatus.getName() + ") -> " +
                updateFormat + " (" + updateAttendanceStatus.getName() + ") 수정 완료!");
    }
}
