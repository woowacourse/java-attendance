package view;

import domain.AttendanceState;
import domain.Calender;
import domain.Crew;
import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import util.TodayDateTimeUtil;

public class OutputView {
    //    final int todayDay, final String todayDayOfWeek,
    public static void printTodayAttendance(
            final String schoolStartTime, final String attendanceResult) {
        System.out.printf("12월 %02d일 %s %s (%s)\n", TodayDateTimeUtil.nowMonth(), TodayDateTimeUtil.nowDayOfWeek(),
                schoolStartTime, attendanceResult);
    }

    public static void printUpdateAttendance(final LocalDateTime beforeDateTime, final LocalDateTime afterDateTime) {

        String beforeAttendanceState = AttendanceState.findStateBy(beforeDateTime.toLocalTime(),
                beforeDateTime.getDayOfMonth());

        String afterAttendanceState = AttendanceState.findStateBy(afterDateTime.toLocalTime(),
                afterDateTime.getDayOfMonth());

        System.out.printf("12월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n", beforeDateTime.getDayOfMonth(),
                Calender.findBy(beforeDateTime.getDayOfMonth()), beforeDateTime.getHour(), beforeDateTime.getMinute(),
                beforeAttendanceState, afterDateTime.getHour(), afterDateTime.getMinute(), afterAttendanceState);
    }

    public static void printRecordAttendance(List<AttendanceResultDto> attendanceResultDtos) {
        for (AttendanceResultDto attendanceResultDto : attendanceResultDtos) {
            System.out.printf(printDayAttendance(attendanceResultDto));
        }
    }

    private static String printDayAttendance(AttendanceResultDto attendanceResultDto) {
        if (attendanceResultDto.localDateTime().getHour() != 0) {
            return String.format("12월 %02d일 %s %02d:%02d (%s)\n", attendanceResultDto.localDateTime().getDayOfMonth(),
                    Calender.findBy(attendanceResultDto.localDateTime().getDayOfMonth()),
                    attendanceResultDto.localDateTime().getHour(), attendanceResultDto.localDateTime().getMinute(),
                    attendanceResultDto.attendanceState());
        }
        return String.format("12월 %02d일 %s --:-- (%s)\n", attendanceResultDto.localDateTime().getDayOfMonth(),
                Calender.findBy(attendanceResultDto.localDateTime().getDayOfMonth()),
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
