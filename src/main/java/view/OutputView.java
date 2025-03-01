package view;

import static constant.OutputViewMessage.ATTENDANCE_CHECK_IN_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_UPDATE_NULL_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_UPDATE_RESPONSE;

import dto.AttendanceCheckInResponse;
import dto.AttendanceUpdateResponse;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceType;

public class OutputView {

    private OutputView() {
    }

    public static void printCheckInAttendance(AttendanceCheckInResponse response) {
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

    public static void printUpdateAttendance(AttendanceUpdateResponse response) {
        if (response.previousTime() == null) {
            println(String.format(ATTENDANCE_UPDATE_NULL_RESPONSE.getMessage(),
                    response.date().getMonthValue(),
                    response.date().getDayOfMonth(),
                    response.date().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                    parseAttendanceType(response.previousAttendanceType()),
                    response.updateTime().getHour(),
                    response.updateTime().getMinute(),
                    parseAttendanceType(response.updateAttendanceType())
            ));
            printNewLine();
            return;
        }
        println(String.format(ATTENDANCE_UPDATE_RESPONSE.getMessage(),
                response.date().getMonthValue(),
                response.date().getDayOfMonth(),
                response.date().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                response.previousTime().getHour(),
                response.previousTime().getMinute(),
                parseAttendanceType(response.previousAttendanceType()),
                response.updateTime().getHour(),
                response.updateTime().getMinute(),
                parseAttendanceType(response.updateAttendanceType())
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
