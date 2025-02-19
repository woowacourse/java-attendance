package view;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.AttendanceHistories;
import java.util.Map;

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
        String result = Parser.parseDateFormat(attendanceHistoryDto.month(), attendanceHistoryDto.day())
                + " "
                + Parser.parseDayOfWeek(attendanceHistoryDto.dayOfWeek())
                + " ";

        if (attendanceHistoryDto.hour() == 0 && attendanceHistoryDto.minute() == 0) {
            result += "--:--"
                    + " ("
                    + attendanceHistoryDto.type().getName()
                    + ") ";
            return result;
        }

        result += Parser.parseTimeFormat(attendanceHistoryDto.hour(), attendanceHistoryDto.minute()) + " ("
                + attendanceHistoryDto.type().getName()
                + ") ";

        return result;
    }

    public static void printAttendanceHistories(Map<Integer, AttendanceHistoryDto> histories) {
        for (Map.Entry<Integer, AttendanceHistoryDto> entry : histories.entrySet()) {
            AttendanceHistoryDto historyDto = entry.getValue();
            System.out.println(getHistoryFormat(historyDto));
        }
    }
}
