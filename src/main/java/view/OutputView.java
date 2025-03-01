package view;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void displayAttendanceCheck(LocalDate nowDate, LocalTime attendTime, String status) {
        String dayOfWeek = nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %02d일 %s ", nowDate.getMonthValue(), nowDate.getDayOfMonth(), dayOfWeek);
        System.out.printf("%02d:%02d (%s)%n", attendTime.getHour(), attendTime.getMinute(), status);
    }

    public void displayAttendanceEdit(LocalDate date, Attendance originAttendance, Attendance updatedAttendance) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %02d일 %s ", date.getMonthValue(), date.getDayOfMonth(), dayOfWeek);
        printAttendanceInfo(originAttendance);
        System.out.printf("%02d:%02d (%s) 수정 완료!%n", updatedAttendance.getTime().getHour(),
                updatedAttendance.getTime().getMinute(), updatedAttendance.determineStatus().getDescription());
    }

    private void printAttendanceInfo(Attendance originAttendance) {
        if (originAttendance == null) {
            System.out.printf("--:-- (%s) -> ", AttendanceStatus.ABSENCE.getDescription());
            return;
        }
        System.out.printf("%02d:%02d (%s) -> ", originAttendance.getTime().getHour(),
                originAttendance.getTime().getMinute(), originAttendance.determineStatus().getDescription());
    }
}
