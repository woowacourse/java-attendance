package view;

import controller.dto.AttendanceHistoryDto;

public class OutputView {
    public static void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printCheckedHistory(AttendanceHistoryDto attendanceHistoryDto) {
        String stringBuilder = Parser.parseDateFormat(attendanceHistoryDto.month(), attendanceHistoryDto.day())
                + " "
                + Parser.parseDayOfWeek(attendanceHistoryDto.dayOfWeek())
                + " "
                + Parser.parseTimeFormat(attendanceHistoryDto.hour(), attendanceHistoryDto.minute())
                + " ("
                + attendanceHistoryDto.type().getName()
                + ") ";
        System.out.println(stringBuilder);
    }
}
