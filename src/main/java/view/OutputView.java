package view;

import domain.AttendanceState;
import dto.AbsenceRecordDto;
import dto.AttendanceHistoryDto;
import dto.AttendanceRecord;
import dto.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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

    public static void printRecordAttendance(final AttendanceHistoryDto attendanceHistoryDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", attendanceHistoryDto.crew().getName());
        System.out.println();

        List<AttendanceRecord> records = attendanceHistoryDto.records();
        for (AttendanceRecord record : records) {
            System.out.printf(
                    String.format("%02d월 %02d일 %s %02d:%02d (%s)\n",
                            DateTimeUtil.getMonthBy(record.date()),
                            DateTimeUtil.getDateBy(record.date()),
                            DateTimeUtil.getDayOfWeekBy(record.date()),
                            record.time().time().getHour(),
                            record.time().time().getMinute(),
                            record.time().state().getDescription()));
        }

        AttendanceStatus attendanceStatus = attendanceHistoryDto.attendanceStatus();
        System.out.printf("출석: %d회\n", attendanceStatus.absenceHistory().attendance());
        System.out.printf("지각: %d회\n", attendanceStatus.absenceHistory().lateness());
        System.out.printf("결석: %d회\n", attendanceStatus.absenceHistory().absence());
        System.out.println();

        System.out.printf("%s 대상자입니다.\n", attendanceStatus.absencePolicy().getDescription());
    }

    public static void printAbsenceResult(final List<AbsenceRecordDto> absenceRecordDtos) {
        System.out.println("제적 위험자 조회 결과");
        absenceRecordDtos.forEach(dto -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                dto.crew().getName(),
                dto.absence(),
                dto.lateness(),
                dto.absencePolicy().getDescription()));
    }
}
