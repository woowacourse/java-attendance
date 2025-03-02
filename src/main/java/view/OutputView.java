package view;

import domain.AttendanceStatus;
import domain.CheckInDate;
import domain.CheckInTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void printTodayCheckInTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        AttendanceStatus status = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), checkInTime.toLocalTime());
        System.out.println(formatDate(checkInDate.toLocalDate())
                + " " + formatTime(checkInTime.toLocalTime())
                + " (" + attendanceStatusToString(status) + ")");
    }

    public void printModifiedChSeckInTime(CheckInDate checkInDate, CheckInTime beforeTime, CheckInTime afterTime) {
        AttendanceStatus beforeStatus = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), beforeTime.toLocalTime());
        AttendanceStatus afterStatus = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), afterTime.toLocalTime());
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료! \n"
                , formatDate(checkInDate.toLocalDate())
                , formatTime(beforeTime.toLocalTime())
                , attendanceStatusToString(beforeStatus)
                , formatTime(afterTime.toLocalTime())
                , attendanceStatusToString(afterStatus)
        );
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

    private String formatDate(LocalDate date) {
        String datePart = date.format(DATE_FORMATTER);
        String dayOfWeek = date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.KOREAN);
        return datePart + " " + dayOfWeek;
    }

    private String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
