package view;

import domain.Attendance;
import domain.AttendanceType;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";
    private static final String CHECKIN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private static final String SUCCESS = "출석";
    private static final String LATE = "지각";
    private static final String ABSENCE = "결석";

    public void printErrorMessage(RuntimeException e) {
        printEmptyLine();
        System.out.printf(ERROR_MESSAGE_FORMAT, e.getMessage());
    }

    public void printCheckInResult(Attendance attendance) {
        LocalDateTime checkInTime = attendance.getAttendanceTime().getTime();
        System.out.printf(
                CHECKIN_FORMAT,
                checkInTime.getMonthValue(),
                checkInTime.getDayOfMonth(),
                checkInTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                checkInTime.getHour(),
                checkInTime.getMinute(),
                convertToAttendanceTypeString(attendance.judgeType())
        );
    }

    private String convertToAttendanceTypeString(AttendanceType attendanceType) {
        if (attendanceType.equals(AttendanceType.SUCCESS)) {
            return SUCCESS;
        }
        if (attendanceType.equals(AttendanceType.LATE)) {
            return LATE;
        }
        if (attendanceType.equals(AttendanceType.ABSENCE)) {
            return ABSENCE;
        }
        return "";
    }

    private void printEmptyLine() {
        System.out.println();
    }
}
