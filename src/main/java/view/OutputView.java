package view;

import domain.attendance.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import static util.DateTimeUtils.*;

public class OutputView {
    public static void printWelcomeMessage() {
        StringBuilder welcomeMessage = new StringBuilder();
        LocalDate today = LocalDate.now();
        welcomeMessage
                .append("오늘은 ")
                .append(today.format(localDayFormatter))
                .append(today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN))
                .append("입니다. 기능을 선택해 주세요.");
        System.out.println(welcomeMessage);
    }

    public static void printAddAttendance(LocalDateTime attendanceTime, AttendanceStatus status){
        StringBuilder attendResult = new StringBuilder();
        attendResult
                .append(attendanceTime.format(dateTimeformatter))
                .append(attendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN))
                .append(attendanceTime.format(dateTimeformatter))
                .append("(").append(status.getStatus()).append(")");
        System.out.println(attendResult);
    }
}
