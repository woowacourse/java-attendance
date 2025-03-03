package view;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void printAttend(AttendanceRecord attendanceRecord) {
        // 12월 13일 금요일 09:59 (출석)
        System.out.println(formatAttendanceRecordToAttend(attendanceRecord));
    }

    private String formatAttendanceRecordToAttend(AttendanceRecord attendanceRecord) {
        return String.format("%s %s %s (%s)", formatLocalDate(attendanceRecord.getDate()), formatDayOfWeek(
                        attendanceRecord.getDate()
                                .getDayOfWeek()), formatLocalTime(attendanceRecord.getTime()),
                formatAttendanceStatus(AttendanceStatus.calculateAttendanceStatus(attendanceRecord)));
    }

    private String formatLocalDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MM월 dd일"));
    }

    private String formatDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    private String formatLocalTime(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String formatAttendanceStatus(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.ABSENT) {
            return "결석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "출석";
    }
}
