package view;

import static constant.OutputViewMessage.ATTENDANCE_CHECK_IN_RESPONSE;

import dto.AttendanceCheckInResponse;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceType;

public class OutputView {

    private OutputView() {
    }

    public static void printCheckIn(AttendanceCheckInResponse response) {
        println(String.format(ATTENDANCE_CHECK_IN_RESPONSE.getMessage(),
                response.checkInDate().getMonthValue(),
                response.checkInDate().getDayOfMonth(),
                response.checkInDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                response.checkInTime().getHour(),
                response.checkInTime().getMinute(),
                parseAttendanceType(response.attendanceType())
        ));
        printNewLine();
    }

    private static String parseAttendanceType(AttendanceType attendanceType) {
        if (attendanceType.equals(AttendanceType.SUCCESS)) {
            return "출석";
        }
        if (attendanceType.equals(AttendanceType.BE_LATE)) {
            return "지각";
        }
        return "결석";
    }

    private static void println(String message) {
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.println();
    }
}
