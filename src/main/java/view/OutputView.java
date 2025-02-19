package view;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceUpdateResultDto;

public class OutputView {
    public static void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printCheckedHistory(AttendanceHistoryDto attendanceHistoryDto) {
        System.out.println(getHistoryFormat(attendanceHistoryDto));
    }

    public static void printUpdatedResult(AttendanceUpdateResultDto attendanceUpdateResultDto) {
        System.out.print(getHistoryFormat(attendanceUpdateResultDto.beforeHistoryDto()));
        System.out.print(" -> ");

        AttendanceHistoryDto afterHistoryDto = attendanceUpdateResultDto.afterHistoryDto();

        System.out.print(Parser.parseTimeFormat(afterHistoryDto.hour(), afterHistoryDto.minute()));
        System.out.println(" (" + afterHistoryDto.type().getName() + ") 수정 완료!");
    }

    private static String getHistoryFormat(AttendanceHistoryDto attendanceHistoryDto) {
        return Parser.parseDateFormat(attendanceHistoryDto.month(), attendanceHistoryDto.day())
                + " "
                + Parser.parseDayOfWeek(attendanceHistoryDto.dayOfWeek())
                + " "
                + Parser.parseTimeFormat(attendanceHistoryDto.hour(), attendanceHistoryDto.minute())
                + " ("
                + attendanceHistoryDto.type().getName()
                + ") ";
    }
}
