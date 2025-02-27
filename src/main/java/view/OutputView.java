package view;

import domain.AttendanceStatus;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void printAlreadyCheckedGuide(String message) {
        System.out.println(message);
    }

    public void printException(String message) {
        System.out.println("[ERROR] : " + message);
    }

    public void printCheckAttendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {

        System.out.printf("12월 %d일 %s %02d:%02d (%s)\n",
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                dateTime.getHour(),
                dateTime.getMinute(),
                attendanceStatus.getKorean());
    }
}
