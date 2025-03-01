package view;

import static constant.OutputViewMessage.ATTENDANCE_ABSENCE_TYPE_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_BE_LATE_TYPE_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_CHECK_IN_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_HISTORY_NULL_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_HISTORY_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_PUNISHMENT_TYPE_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_SUCCESS_TYPE_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_UPDATE_NULL_RESPONSE;
import static constant.OutputViewMessage.ATTENDANCE_UPDATE_RESPONSE;

import dto.AttendanceCheckInResponse;
import dto.AttendanceHistoryResponse;
import dto.AttendanceUpdateResponse;
import java.time.format.TextStyle;
import java.util.Locale;
import model.Attendance;
import model.AttendanceType;
import model.PunishmentType;

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

    public static void printAttendanceHistory(AttendanceHistoryResponse response) {
        for (Attendance attendance : response.attendances()) {
            if (attendance.getCheckInTime() == null) {
                println(String.format(ATTENDANCE_HISTORY_NULL_RESPONSE.getMessage(),
                        attendance.getCheckInDate().getMonthValue(),
                        attendance.getCheckInDate().getDayOfMonth(),
                        attendance.getCheckInDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                        parseAttendanceType(attendance.getAttendanceType())
                ));
                continue;
            }
            println(String.format(ATTENDANCE_HISTORY_RESPONSE.getMessage(),
                    attendance.getCheckInDate().getMonthValue(),
                    attendance.getCheckInDate().getDayOfMonth(),
                    attendance.getCheckInDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                    attendance.getCheckInTime().getHour(),
                    attendance.getCheckInTime().getMinute(),
                    parseAttendanceType(attendance.getAttendanceType())
            ));
        }
        printNewLine();

        println(String.format(ATTENDANCE_SUCCESS_TYPE_RESPONSE.getMessage(),
                response.attendanceTotal().get(AttendanceType.SUCCESS)));
        println(String.format(ATTENDANCE_BE_LATE_TYPE_RESPONSE.getMessage(),
                response.attendanceTotal().get(AttendanceType.BE_LATE)));
        println(String.format(ATTENDANCE_ABSENCE_TYPE_RESPONSE.getMessage(),
                response.attendanceTotal().get(AttendanceType.ABSENCE)));
        printNewLine();

        if (!response.punishmentType().equals(PunishmentType.NONE)) {
            println(String.format(ATTENDANCE_PUNISHMENT_TYPE_RESPONSE.getMessage(),
                    parsePunishmentType(response.punishmentType())));
            printNewLine();
        }
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

    private static String parsePunishmentType(PunishmentType punishmentType) {
        if (punishmentType.equals(PunishmentType.WARNING)) {
            return "경고";
        }
        if (punishmentType.equals(PunishmentType.MEETING)) {
            return "면담";
        }
        if (punishmentType.equals(PunishmentType.EXPULSION)) {
            return "제적";
        }
        return "";
    }

    private static void println(String message) {
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.println();
    }
}
