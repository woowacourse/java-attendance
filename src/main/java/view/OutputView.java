package view;

import domain.Attendance;
import domain.AttendanceType;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";
    private static final String CHECKIN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private static final String ABSENCE_FORMAT = "--:-- (결석) ";
    private static final String DATE_FORMAT = "%d월 %02d일 %s ";
    private static final String ARROW = "-> ";
    private static final String TIME_FORMAT = "%02d:%02d (%s) ";
    private static final String MODIFY_COMPLETE = "수정 완료!%n";
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

    public void printModifiedResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.printf(
                DATE_FORMAT,
                oldAttendance.getAttendanceTime().getTime().getMonthValue(),
                oldAttendance.getAttendanceTime().getTime().getDayOfMonth(),
                oldAttendance.getAttendanceTime().getTime().getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, Locale.getDefault())
        );
        if (oldAttendance.judgeType() == AttendanceType.ABSENCE) {
            System.out.print(ABSENCE_FORMAT);
        }
        if (oldAttendance.judgeType() != AttendanceType.ABSENCE) {
            System.out.printf(
                    TIME_FORMAT,
                    oldAttendance.getAttendanceTime().getTime().getHour(),
                    oldAttendance.getAttendanceTime().getTime().getMinute(),
                    convertToAttendanceTypeString(oldAttendance.judgeType())
            );
        }
        System.out.print(ARROW);
        if (newAttendance.judgeType() == AttendanceType.ABSENCE) {
            System.out.print(ABSENCE_FORMAT);
        }
        if (newAttendance.judgeType() != AttendanceType.ABSENCE) {
            System.out.printf(
                    TIME_FORMAT,
                    newAttendance.getAttendanceTime().getTime().getHour(),
                    newAttendance.getAttendanceTime().getTime().getMinute(),
                    convertToAttendanceTypeString(newAttendance.judgeType())
            );
        }
        System.out.printf(MODIFY_COMPLETE);
    }

    private void printEmptyLine() {
        System.out.println();
    }
}
