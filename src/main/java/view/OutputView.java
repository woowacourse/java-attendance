package view;

import domain.Attendance;
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
        System.out.printf("12월 %02d일 %s %s (%s)\n",
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                getTimePrintForm(dateTime),
                attendanceStatus.getKorean());
    }

    public void printChangeAttendance(Attendance originalAttendance, Attendance changeAttendance) {
        LocalDateTime originalDateTime =originalAttendance.getDateTime();
        LocalDateTime changeDateTime = changeAttendance.getDateTime();
        System.out.printf("12월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                originalDateTime.getDayOfMonth(),
                originalDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                getTimePrintForm(originalDateTime),
                originalAttendance.calculateAttendanceStatus().getKorean(),
                getTimePrintForm(changeDateTime),
                changeAttendance.calculateAttendanceStatus().getKorean());
    }

    private String getTimePrintForm(LocalDateTime dateTime) {
        if(dateTime.getHour() == 23 && dateTime.getMinute() == 59) {
            return "--:--";
        }
        return String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
    }
}
