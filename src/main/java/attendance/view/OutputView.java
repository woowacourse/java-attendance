package attendance.view;

import attendance.model.AttendanceStatus;
import attendance.model.AttendanceTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }

    public void printAttendance(final AttendanceTime attendanceTime) {
        final int year = attendanceTime.getDate().getYear();
        final int date = attendanceTime.getDate().getDayOfMonth();
        final String day = attendanceTime.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        final int hour = attendanceTime.getHour();
        final int minute = attendanceTime.getMinute();
        final String status = AttendanceStatus.getAttendanceStatus(attendanceTime).getValue();

        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)", year, date, day, hour, minute, status);
    }

    public void printAfterAttendance(final AttendanceTime attendanceTime) {

        final int hour = attendanceTime.getHour();
        final int minute = attendanceTime.getMinute();
        final String status = AttendanceStatus.getAttendanceStatus(attendanceTime).getValue();
        System.out.printf(" -> %02d:%02d (%s) 수정 완료!\n", hour, minute, status);
    }

    public void printLine() {
        System.out.println();
    }
}
