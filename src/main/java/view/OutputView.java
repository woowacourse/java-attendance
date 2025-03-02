package view;

import controller.AttendanceController;
import domain.AttendanceStatus;
import domain.CheckInDate;
import domain.CheckInTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void printTodayCheckInTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        AttendanceStatus status = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), checkInTime.toLocalTime());
        System.out.println(formatDate(checkInDate.toLocalDate())
                + " " + formatTime(checkInTime.toLocalTime())
                + " (" + attendanceStatusToString(status) + ")");
    }

    private static String attendanceStatusToString(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.PRESENCE) {
            return "출석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "결석";
    }

    private static String formatDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
        String datePart = date.format(dateFormatter);

        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        return datePart + " " + dayOfWeek;
    }

    private static String formatTime(LocalTime time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(AttendanceController.HOUR_MINUTE_FORMAT);
        String timePart = time.format(timeFormatter);
        return timePart;
    }
}
