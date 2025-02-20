package view;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceHistoryWithPenaltyTypeDto;
import controller.dto.AttendanceTypeCountDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.AttendanceHistories;
import domain.AttendanceType;
import domain.PenaltyType;
import java.util.List;
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
        String result = Parser.parseDateFormat(attendanceHistoryDto.month(), attendanceHistoryDto.day()) + " "
                + Parser.parseDayOfWeek(attendanceHistoryDto.dayOfWeek()) + " ";

        if (attendanceHistoryDto.hour() == 0 && attendanceHistoryDto.minute() == 0) {
            result += "--:--" + " (" + attendanceHistoryDto.type().getName() + ") ";
            return result;
        }

        result += Parser.parseTimeFormat(attendanceHistoryDto.hour(), attendanceHistoryDto.minute()) + " ("
                + attendanceHistoryDto.type().getName() + ") ";

        return result;
    }

    // TODO: 출석 지각 결석 현황 + 면담 대상자 여부 출력해야 함

    public static void printAttendanceHistories(AttendanceHistoryWithPenaltyTypeDto dto) {
        int attendanceCount = 0;
        int lateCount = 0;
        int absenceCount = 0;

        for (Map.Entry<Integer, AttendanceHistoryDto> entry : dto.historyDtoOfDay().entrySet()) {
            AttendanceHistoryDto historyDto = entry.getValue();

            if (historyDto.type() == AttendanceType.PRESENT) {
                attendanceCount++;
            }

            if (historyDto.type() == AttendanceType.LATE) {
                lateCount++;
            }

            if (historyDto.type() == AttendanceType.ABSENCE) {
                absenceCount++;
            }

            System.out.println(getHistoryFormat(historyDto));
        }

        System.out.println();
        System.out.printf("출석: %d회%n지각: %d회%n결석: %d회%n%n", attendanceCount, lateCount, absenceCount);

        if (dto.penaltyType() == PenaltyType.NONE) {
            return;
        }

        System.out.println(Parser.parsePenaltyTypeFormat(dto.penaltyType()) + " 대상자입니다.");
    }

    public static void printBanWarningCrews(List<AttendanceTypeCountDto> attendanceTypeCountDtos) {
        for (AttendanceTypeCountDto attendanceTypeCountDto : attendanceTypeCountDtos) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", attendanceTypeCountDto.nickname(),
                    attendanceTypeCountDto.absenceCount(), attendanceTypeCountDto.lateCount(),
                    Parser.parsePenaltyTypeFormat(attendanceTypeCountDto.penaltyType()));
        }
    }
}
