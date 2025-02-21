package view;

import domain.AttendanceState;
import domain.Crew;
import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import util.DateTimeUtil;

public class OutputView {
    public static void printTodayAttendance(
            final String schoolStartTime, final String attendanceResult) {
        System.out.printf("%02d월 %02d일 %s %s (%s)\n",
                DateTimeUtil.getMonthBy(LocalDate.now()),
                DateTimeUtil.getDateBy(LocalDate.now()),
                DateTimeUtil.getDayOfWeekBy(LocalDate.now()),
                schoolStartTime, attendanceResult);
    }

    public static void printUpdateAttendance(final LocalDateTime beforeDateTime, final LocalDateTime afterDateTime) {

        String beforeAttendanceState = AttendanceState.findStateBy(
                        beforeDateTime.toLocalTime(), beforeDateTime.toLocalDate())
                .getDescription();

        String afterAttendanceState = AttendanceState.findStateBy(
                        afterDateTime.toLocalTime(), afterDateTime.toLocalDate())
                .getDescription();

        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                DateTimeUtil.getYearBy(beforeDateTime.toLocalDate()),
                DateTimeUtil.getDateBy(beforeDateTime.toLocalDate()),
                DateTimeUtil.getDayOfWeekBy(beforeDateTime.toLocalDate()),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                beforeAttendanceState,
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                afterAttendanceState);
    }

    public static void printRecordAttendance(List<AttendanceResultDto> attendanceResultDtos) {
        for (AttendanceResultDto attendanceResultDto : attendanceResultDtos) {
            System.out.printf(printDayAttendance(attendanceResultDto));
        }
    }

    private static String printDayAttendance(AttendanceResultDto attendanceResultDto) {
        if (attendanceResultDto.localDateTime().getHour() != 0) {
            return String.format("%02d월 %02d일 %s %02d:%02d (%s)\n",
                    DateTimeUtil.getMonthBy(attendanceResultDto.localDateTime().toLocalDate()),
                    DateTimeUtil.getDateBy(attendanceResultDto.localDateTime().toLocalDate()),
                    DateTimeUtil.getDayOfWeekBy(attendanceResultDto.localDateTime().toLocalDate()),
                    attendanceResultDto.localDateTime().getHour(),
                    attendanceResultDto.localDateTime().getMinute(),
                    attendanceResultDto.attendanceState());
        }
        return String.format("%02d월 %02d일 %s --:-- (%s)\n",
                DateTimeUtil.getMonthBy(attendanceResultDto.localDateTime().toLocalDate()),
                DateTimeUtil.getDateBy(attendanceResultDto.localDateTime().toLocalDate()),
                DateTimeUtil.getDayOfWeekBy(attendanceResultDto.localDateTime().toLocalDate()),
                attendanceResultDto.attendanceState());
    }

    public static void printAbsenceHistory(AbsenceResultDto absenceResultDto) {
        System.out.printf("출석: %d회\n", absenceResultDto.attendance());
        System.out.printf("지각: %d회\n", absenceResultDto.lateness());
        System.out.printf("결석: %d회\n", absenceResultDto.absence());

        System.out.println();

        System.out.printf("%s 대상자입니다.\n", absenceResultDto.status());
    }

    public static void printAbsenceResult(final Map<Crew, AbsenceResultDto> result) {
        System.out.println("제적 위험자 조회 결과");
        result.forEach((crew, absenceResult) -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                crew.getName(),
                absenceResult.absence(),
                absenceResult.lateness(),
                absenceResult.status()));
    }
}
