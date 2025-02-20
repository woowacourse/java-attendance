package view;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.CheckInTime;
import domain.PenaltyStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printTodayCheckInTime(CheckInTime time) {
        LocalDateTime localDateTime = time.toLocalDateTime();
        AttendanceStatus attendanceStatus = time.getAttendanceStatus();
        System.out.println(formatDateTime(localDateTime) + " (" + attendanceStatusToString(attendanceStatus) + ")");
    }

    private String attendanceStatusToString(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.PRESENCE) {
            return "출석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "결석";
    }

    public void printModifyCheckInTime(CheckInTime before, CheckInTime after) {
        System.out.println(before.toLocalDateTime() + " -> " + after.toLocalDateTime());
    }

    public void printAttendanceLog(Attendance attendance) {
        for (LocalDateTime time : attendance.getAttendanceLog()) {
            System.out.println(time);
        }
        int presenceCount = attendance.countPresence();
        int lateCount = attendance.countLate();
        int absenceCount = attendance.countAbsence();
        PenaltyStatus penaltyStatus = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);

        System.out.println("출석: " + presenceCount);
        System.out.println("지각: " + lateCount);
        System.out.println("결석: " + absenceCount);
        System.out.println(penaltyStatus.name());
    }

    public void printDangerCrews(List<Attendance> dangerCrews) {
        for (Attendance attendance : dangerCrews) {
            System.out.println(attendance);
        }
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
        String datePart = localDateTime.format(dateFormatter);

        String dayOfWeek = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String timePart = localDateTime.format(timeFormatter);

        return datePart + " " + dayOfWeek + " " + timePart;
    }
}
