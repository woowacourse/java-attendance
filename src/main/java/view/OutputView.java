package view;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceHistoryWithPenaltyTypeDto;
import controller.dto.AttendanceTypeCountDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.attendance.AttendanceType;
import domain.attendance.PenaltyType;
import java.util.Comparator;
import java.util.HashMap;
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

    public static void printAttendanceHistories(AttendanceHistoryWithPenaltyTypeDto dto) {
        Map<AttendanceType, Integer> attendanceTypeCount = new HashMap<>();
        for (Map.Entry<Integer, AttendanceHistoryDto> entry : dto.historyDtoOfDay().entrySet()) {
            AttendanceHistoryDto historyDto = entry.getValue();
            attendanceTypeCount.merge(historyDto.type(), 1, Integer::sum);
            System.out.println(getHistoryFormat(historyDto));
        }

        System.out.println();
        System.out.printf("출석: %d회%n지각: %d회%n결석: %d회%n%n", attendanceTypeCount.getOrDefault(AttendanceType.PRESENT, 0),
                attendanceTypeCount.getOrDefault(AttendanceType.LATE, 0),
                attendanceTypeCount.getOrDefault(AttendanceType.ABSENCE, 0));

        if (dto.penaltyType() == PenaltyType.NONE) {
            return;
        }

        System.out.println(Parser.parsePenaltyTypeFormat(dto.penaltyType()) + " 대상자입니다.");
    }

    public static void printBanWarningCrews(List<AttendanceTypeCountDto> attendanceTypeCountDtos) {
        Comparator<AttendanceTypeCountDto> penaltyTypeComparator = (p1, p2) -> {
            List<PenaltyType> order = List.of(PenaltyType.BAN, PenaltyType.ONE_ON_ONE, PenaltyType.WARNING);
            return Integer.compare(order.indexOf(p1.penaltyType()), order.indexOf(p2.penaltyType()));
        };

        Comparator<AttendanceTypeCountDto> absenceComparator = (p1, p2) -> {
            int totalAbsenceCountOfP1 = p1.absenceCount() + p1.lateCount() / 3;
            int totalAbsenceCountOfP2 = p2.absenceCount() + p2.lateCount() / 3;
            return Integer.compare(totalAbsenceCountOfP2, totalAbsenceCountOfP1);
        };

        // 정렬
        // 기준 제적 > 면담 > 경고 > 결석횟수 > 닉네임
        List<AttendanceTypeCountDto> sortedAttendanceTypeCountDto = attendanceTypeCountDtos.stream()
                .sorted(penaltyTypeComparator
                        .thenComparing(absenceComparator)
                        .thenComparing(AttendanceTypeCountDto::nickname)).toList();

        for (AttendanceTypeCountDto attendanceTypeCountDto : sortedAttendanceTypeCountDto) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", attendanceTypeCountDto.nickname(),
                    attendanceTypeCountDto.absenceCount(), attendanceTypeCountDto.lateCount(),
                    Parser.parsePenaltyTypeFormat(attendanceTypeCountDto.penaltyType()));
        }
    }
}
