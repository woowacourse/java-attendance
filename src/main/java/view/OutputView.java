package view;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceHistoryWithPenaltyTypeDto;
import controller.dto.AttendanceTypeCountDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.attendance.AttendanceType;
import domain.attendance.PenaltyType;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {
    public static void printTodayMessage(int month, int day, String dayOfWeek) {
        System.out.println();
        System.out.printf("오늘은 %02d월 %02d일 %s입니다. ", month, day, dayOfWeek);
    }

    public static void printErrorMessage(String message) {
        System.out.println();
        System.out.println("[ERROR] " + message);
    }

    public static void printAttendanceDayErrorMessage(int month, int day, DayOfWeek dayOfWeek) {
        System.out.println();
        System.out.printf("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.%n", month, day, dayOfWeek.getDisplayName(TextStyle.FULL,
                Locale.KOREAN));
    }

    public static void printCheckedHistory(AttendanceHistoryDto attendanceHistoryDto) {
        System.out.println();
        System.out.println(getHistoryFormat(attendanceHistoryDto));
    }

    public static void printUpdatedResult(AttendanceUpdateResultDto attendanceUpdateResultDto) {
        System.out.println();
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

    public static void printAttendanceHistories(String nickname, AttendanceHistoryWithPenaltyTypeDto dto) {
        int attendanceCount = 0;
        int lateCount = 0;
        int absenceCount = 0;

        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", nickname);

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
        Comparator<AttendanceTypeCountDto> penaltyTypeComparator = (p1, p2) -> {
            List<PenaltyType> order = List.of(PenaltyType.BAN, PenaltyType.ONE_ON_ONE, PenaltyType.WARNING);
            return Integer.compare(order.indexOf(p1.penaltyType()), order.indexOf(p2.penaltyType()));
        };

        Comparator<AttendanceTypeCountDto> absenceComparator = (p1, p2) -> {
            int totalAbsenceCountOfP1 = p1.absenceCount() + p1.lateCount() / 3;
            int totalAbsenceCountOfP2 = p2.absenceCount() + p2.lateCount() / 3;
            return Integer.compare(totalAbsenceCountOfP2, totalAbsenceCountOfP1);
        };

        List<AttendanceTypeCountDto> sortedAttendanceTypeCountDto = attendanceTypeCountDtos.stream()
                .sorted(penaltyTypeComparator
                        .thenComparing(absenceComparator)
                        .thenComparing(AttendanceTypeCountDto::nickname)).toList();

        System.out.println();
        System.out.println("제적 위험자 조회 결과");

        for (AttendanceTypeCountDto attendanceTypeCountDto : sortedAttendanceTypeCountDto) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", attendanceTypeCountDto.nickname(),
                    attendanceTypeCountDto.absenceCount(), attendanceTypeCountDto.lateCount(),
                    Parser.parsePenaltyTypeFormat(attendanceTypeCountDto.penaltyType()));
        }
    }
}
