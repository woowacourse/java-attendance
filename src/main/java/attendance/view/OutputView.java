package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void printConfirmResult(final LocalDateTime dateTime, final AttendanceStatus status) {
        System.out.printf("%2d월 %2d일 %s %2d:%2d (%s)\n)",
                dateTime.getMonthValue(),
                dateTime.getMonthValue(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                getStatusString(status));
    }

    public void printUpdateResult(final Attendance beforeUpdateAttendance, final Attendance afterUpdateAttendance) {
        LocalDateTime beforeDateTime = beforeUpdateAttendance.getDateTime();
        LocalDateTime afterDateTime = afterUpdateAttendance.getDateTime();
        System.out.printf("%2d월 %2d일 %s %2d:%2d (%s) -> %2d:%2d (%s) 수정 완료!\n",
                beforeDateTime.getMonthValue(),
                beforeDateTime.getDayOfMonth(),
                beforeDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                getStatusString(beforeUpdateAttendance.getStatus()),
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                getStatusString(afterUpdateAttendance.getStatus()));
    }

    private static String getStatusString(final AttendanceStatus status) {
        if(status.equals(AttendanceStatus.ATTEND)) return "춣석";
        if(status.equals(AttendanceStatus.LATE)) return "지각";
        if(status.equals(AttendanceStatus.ABSENCE)) return "결석";
        return "";
    }
}
