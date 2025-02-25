package view;

import domain.AbsencePolicy;
import domain.AttendanceState;
import dto.AbsenceRecordDto;
import dto.AttendanceHistoryDto;
import dto.AttendanceRecord;
import dto.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import util.DateTimeUtil;

public class OutputView {
    public static void printTodayAttendance(
            final String schoolStartTime, final String attendanceResult) {
        System.out.printf("12월 %02d일 %s %s (%s)\n",
                DateTimeUtil.getTodayDate(),
                DateTimeUtil.getDayOfWeekBy(LocalDate.of(2024, 12, DateTimeUtil.getTodayDate())),
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

        String beforeTimeFormatted = formatTime(beforeTime);

        System.out.printf("%02d월 %02d일 %s %s (%s) -> %02d:%02d (%s) 수정 완료!\n",
                DateTimeUtil.getYearBy(localDate),
                DateTimeUtil.getDateBy(localDate),
                DateTimeUtil.getDayOfWeekBy(localDate),
                beforeTimeFormatted,
                beforeAttendanceState,
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                afterAttendanceState);
    }


    public static void printRecordAttendance(final AttendanceHistoryDto attendanceHistoryDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", attendanceHistoryDto.crew().getName());
        System.out.println();

        List<AttendanceRecord> records = attendanceHistoryDto.records();
        List<AttendanceRecord> sortedRecords = sortByDate(records);
        for (AttendanceRecord record : sortedRecords) {
            String timeFormatted = formatTime(record.time().time());

            System.out.printf(
                    String.format("%02d월 %02d일 %s %s (%s)\n",
                            DateTimeUtil.getMonthBy(record.date()),
                            DateTimeUtil.getDateBy(record.date()),
                            DateTimeUtil.getDayOfWeekBy(record.date()),
                            timeFormatted,
                            record.time().state().getDescription()));
        }

        AttendanceStatus attendanceStatus = attendanceHistoryDto.attendanceStatus();
        System.out.printf("출석: %d회\n", attendanceStatus.absenceHistory().attendance());
        System.out.printf("지각: %d회\n", attendanceStatus.absenceHistory().lateness());
        System.out.printf("결석: %d회\n", attendanceStatus.absenceHistory().absence());
        System.out.println();

        System.out.printf("%s 대상자입니다.\n", attendanceStatus.absencePolicy().getDescription());
    }

    private static List<AttendanceRecord> sortByDate(List<AttendanceRecord> attendanceRecords) {
        return attendanceRecords.stream()
                .sorted(Comparator.comparing(AttendanceRecord::date))
                .toList();
    }

    private static String formatTime(LocalTime beforeTime) {
        if (beforeTime.equals(LocalTime.of(0, 0))) {
            return "--:--";
        }
        return String.format("%02d:%02d", beforeTime.getHour(), beforeTime.getMinute());
    }

    public static void printAbsenceResult(final List<AbsenceRecordDto> absenceRecordDtos) {
        System.out.println("제적 위험자 조회 결과");
        sortAbsenceRecordDtos(absenceRecordDtos).forEach(dto -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                dto.crew().getName(),
                dto.absence(),
                dto.lateness(),
                dto.absencePolicy().getDescription()));
    }

    private static List<AbsenceRecordDto> sortAbsenceRecordDtos(List<AbsenceRecordDto> absenceRecordDtos) {
        return absenceRecordDtos.stream()
                .sorted(Comparator
                        .comparing((AbsenceRecordDto dto) -> getAbsencePriority(dto.absencePolicy()))
                        .thenComparing(dto -> dto.lateness() + dto.absence() * 3, Comparator.reverseOrder())
                        .thenComparing(dto -> dto.crew().getName()))
                .toList();

    }

    private static int getAbsencePriority(AbsencePolicy absencePolicy) {
        if (absencePolicy == AbsencePolicy.DISMISSED) {
            return 0;
        }
        if (absencePolicy == AbsencePolicy.INTERVIEW) {
            return 1;
        }
        if (absencePolicy == AbsencePolicy.WARNING) {
            return 2;
        }
        return 3;
    }
}
