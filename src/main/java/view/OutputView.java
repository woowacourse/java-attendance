package view;

import domain.AttendanceState;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public static void printUpdateAttendance(final LocalTime beforeTime, final LocalDateTime afterDateTime) {
        LocalDate localDate = afterDateTime.toLocalDate();

        String beforeAttendanceState = AttendanceState.findStateBy(
                        beforeTime, localDate)
                .getDescription();

        String afterAttendanceState = AttendanceState.findStateBy(
                        afterDateTime.toLocalTime(), localDate)
                .getDescription();

        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                DateTimeUtil.getYearBy(localDate),
                DateTimeUtil.getDateBy(localDate),
                DateTimeUtil.getDayOfWeekBy(localDate),
                beforeTime.getHour(),
                beforeTime.getMinute(),
                beforeAttendanceState,
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                afterAttendanceState);
    }

//    public static void printRecordAttendance(AttendanceHistoryDto attendanceResultDto) {
//        for (AttendanceHistoryDto dto : attendanceResultDto.localDateTimes()) {
//            System.out.printf(printDayAttendance(dto));
//        }
//    }
//
//    private static String printDayAttendance(AttendanceHistoryDto attendanceResultDto) {
//        if (attendanceResultDto.localDateTime().getHour() != 0) {
//            return String.format("%02d월 %02d일 %s %02d:%02d (%s)\n",
//                    DateTimeUtil.getMonthBy(attendanceResultDto.localDateTime().toLocalDate()),
//                    DateTimeUtil.getDateBy(attendanceResultDto.localDateTime().toLocalDate()),
//                    DateTimeUtil.getDayOfWeekBy(attendanceResultDto.localDateTime().toLocalDate()),
//                    attendanceResultDto.localDateTime().getHour(),
//                    attendanceResultDto.localDateTime().getMinute(),
//                    attendanceResultDto.attendanceState());
//        }
//        return String.format("%02d월 %02d일 %s --:-- (%s)\n",
//                DateTimeUtil.getMonthBy(attendanceResultDto.localDateTime().toLocalDate()),
//                DateTimeUtil.getDateBy(attendanceResultDto.localDateTime().toLocalDate()),
//                DateTimeUtil.getDayOfWeekBy(attendanceResultDto.localDateTime().toLocalDate()),
//                attendanceResultDto.attendanceState());
//    }
//
//    public static void printAbsenceHistory(AbsenceHistoryDto absenceHistoryDto) {
//        System.out.printf("출석: %d회\n", absenceHistoryDto.attendance());
//        System.out.printf("지각: %d회\n", absenceHistoryDto.lateness());
//        System.out.printf("결석: %d회\n", absenceHistoryDto.absence());
//
//        System.out.println();
//
//        System.out.printf("%s 대상자입니다.\n", absenceHistoryDto.status());
//    }
//
//    public static void printAbsenceResult(final Map<Crew, AbsenceHistoryDto> result) {
//        System.out.println("제적 위험자 조회 결과");
//        result.forEach((crew, absenceResult) -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
//                crew.getName(),
//                absenceResult.absence(),
//                absenceResult.lateness(),
//                absenceResult.status()));
//    }
}
