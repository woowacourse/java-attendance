package attendance.view;

import attendance.domain.Attendance;

import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private OutputView() {}

    public static void printRecordAttendanceResult(Attendance attendance) {
        System.out.printf("%n%d월 %d일 %s %02d:%02d (%s)%n%n",
                attendance.getAttendDate().getMonthValue(),
                attendance.getAttendDate().getDayOfMonth(),
                attendance.getAttendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.getAttendTime().getHour(),
                attendance.getAttendTime().getMinute(),
                attendance.determineStatus().getName());
    }

    public static void printEditAttendanceResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.printf("%n%d월 %d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n%n",
                oldAttendance.getAttendDate().getMonthValue(),
                oldAttendance.getAttendDate().getDayOfMonth(),
                oldAttendance.getAttendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                oldAttendance.getAttendTime().getHour(),
                oldAttendance.getAttendTime().getMinute(),
                oldAttendance.determineStatus().getName(),
                newAttendance.getAttendTime().getHour(),
                newAttendance.getAttendTime().getMinute(),
                newAttendance.determineStatus().getName());
    }
}
