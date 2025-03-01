package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceStatus;

public class AttendanceRecordFormatter {
    public static String attendanceRecordFormatter(LocalTime localTime,
                                                   AttendanceStatus attendanceStatus,
                                                   LocalDate localDate) {
        String attendanceTime = localTimeFormatter(localTime);
        return localDate.getMonthValue() + "월" +
                localDate.getDayOfMonth() + "일" +
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA) +
                attendanceTime +
                " (" + attendanceStatus.getAttendanceStatus() + ")";
    }

    private static String localTimeFormatter(LocalTime localTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (localTime == null){
            return "--:--";
        }
        return localTime.format(formatter);
    }
}
