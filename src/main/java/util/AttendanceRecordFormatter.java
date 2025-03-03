package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendancePenalty;
import model.AttendanceStatus;
import model.Student;

public class AttendanceRecordFormatter {
    public static String attendanceRecordFormatter(Student student, LocalDate localDate) {
        LocalTime localTime = student.findAttendanceLocalTimeByLocalDate(localDate);
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateAttendanceStatus(localDate, localTime);
        String attendanceTime = localTimeFormatter(localTime);
        return localDate.getMonthValue() + "월 " +
                localDate.getDayOfMonth() + "일 " +
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA) + " " +
                attendanceTime +
                " (" + attendanceStatus.getAttendanceStatus() + ")";
    }

    public static String expulsionRiskRecordFormatter(Student student) {
        long absent = student.calculateAbsentCount();
        long late = student.calculateLateCount();
        long totalAbsent = student.calculateTotalAbsentCount();
        AttendancePenalty attendancePenalty = AttendancePenalty.findPenaltyByAbsentCount(totalAbsent);
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                student.getName(),
                absent,
                late,
                attendancePenalty.getPenalty());
    }

    private static String localTimeFormatter(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (localTime == null) {
            return "--:--";
        }
        return localTime.format(formatter);
    }
}
