package view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import model.Attendance;
import model.AttendanceType;

public class OutputView {

    private final String CHECK_IN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private final String SUCCESS = "출석";
    private final String BE_LATE = "지각";
    private final String ABSENCE = "결석";

    public void printCheckInResult(Attendance attendance) {
        LocalDateTime checkInTime = attendance.getCheckInTime();
        System.out.printf(
                CHECK_IN_FORMAT,
                checkInTime.getMonthValue(),
                checkInTime.getDayOfMonth(),
                checkInTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                checkInTime.getHour(),
                checkInTime.getMinute(),
                convertToAttendanceTypeString(attendance.getAttendanceType())
        );
    }

    private String convertToAttendanceTypeString(AttendanceType attendanceType) {
        if (attendanceType.equals(AttendanceType.SUCCESS)) {
            return SUCCESS;
        }
        if (attendanceType.equals(AttendanceType.BE_LATE)) {
            return BE_LATE;
        }
        if (attendanceType.equals(AttendanceType.ABSENCE)) {
            return ABSENCE;
        }
        return "";
    }
}
