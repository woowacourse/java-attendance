package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceStatus;

public class AttendanceRecordFormatter {
    public static String attendanceRecordFormatter(LocalTime localTime, AttendanceStatus attendanceStatus, LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localDate.getMonthValue() + "월" +
                localDate.getDayOfMonth() + "일" +
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA) +
                localTime.format(formatter) +
                "(" + attendanceStatus.getAttendanceStatus() + ")";
    }
}
