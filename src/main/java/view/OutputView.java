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
        return String.format("%s %s %s (%s)%n", formatLocalDate(attendanceRecord.getDate()), formatDayOfWeek(
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
        if (localTime == null) {
            return "--:--";
        }
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

    public void printEdit(AttendanceRecord beforeAttendanceRecord, AttendanceRecord editAttendanceRecord) {
        System.out.println(formatAttendanceRecordToEdit(beforeAttendanceRecord, editAttendanceRecord));
    }

    private String formatAttendanceRecordToEdit(AttendanceRecord beforeAttendanceRecord,
                                                AttendanceRecord editAttendanceRecord) {
        return String.format("%s %s %s (%s) -> %s (%s) 수정 완료!%n",
                formatLocalDate(beforeAttendanceRecord.getDate()),
                formatDayOfWeek(beforeAttendanceRecord.getDate()
                        .getDayOfWeek()),
                formatLocalTime(beforeAttendanceRecord.getTime()),
                formatAttendanceStatus(AttendanceStatus.calculateAttendanceStatus(beforeAttendanceRecord)),
                formatLocalTime(editAttendanceRecord.getTime()),
                formatAttendanceStatus(AttendanceStatus.calculateAttendanceStatus(editAttendanceRecord)));
    }

    public void printErrorMessage(RuntimeException e) {
        System.out.println("[ERROR] " + e.getMessage());
        System.out.println();
    }
}
