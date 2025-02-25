package view;

import domain.AbsenceHistory;
import domain.AttendanceState;
import domain.Calender;
import domain.Crew;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printTodayAttendance(final int todayDay, final String todayDayOfWeek,
                                     final LocalTime schoolStartTime, final AttendanceState attendanceResult) {
        System.out.printf("\n12월 %02d일 %s %02d:%02d (%s)\n",
                todayDay,
                todayDayOfWeek,
                schoolStartTime.getHour(),
                schoolStartTime.getMinute(),
                attendanceResult.getDescription());
    }

    public void printUpdateAttendance(final LocalDateTime beforeDateTime, final LocalDateTime afterDateTime) {

        AttendanceState beforeAttendanceState = AttendanceState.findStateBy(beforeDateTime);
        AttendanceState afterAttendanceState = AttendanceState.findStateBy(afterDateTime);

        System.out.printf("12월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                beforeDateTime.getDayOfMonth(),
                Calender.findBy(beforeDateTime.getDayOfWeek()).getDescription(),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                beforeAttendanceState.getDescription(),
                afterDateTime.getHour(), afterDateTime.getMinute(),
                afterAttendanceState.getDescription());
    }

    public void printRecordAttendance(List<AttendanceResultDto> attendanceResultDtos) {
        for (AttendanceResultDto attendanceResultDto : attendanceResultDtos) {
            System.out.printf(printDayAttendance(attendanceResultDto));
        }
    }

    private String printDayAttendance(AttendanceResultDto attendanceResultDto) {
        if (attendanceResultDto.localDateTime().getHour() != 0) {
            return String.format("12월 %02d일 %s %02d:%02d (%s)\n",
                    attendanceResultDto.localDateTime().getDayOfMonth(),
                    Calender.findBy(attendanceResultDto.localDateTime().getDayOfWeek()).getDescription(),
                    attendanceResultDto.localDateTime().getHour(),
                    attendanceResultDto.localDateTime().getMinute(),
                    attendanceResultDto.attendanceState().getDescription());
        }
        return String.format("12월 %02d일 %s --:-- (%s)\n",
                attendanceResultDto.localDateTime().getDayOfMonth(),
                Calender.findBy(attendanceResultDto.localDateTime().getDayOfWeek()).getDescription(),
                attendanceResultDto.attendanceState().getDescription());
    }

    public void printAbsenceHistory(AbsenceHistory absenceResultDto) {
        System.out.printf("\n출석: %d회\n", absenceResultDto.attendance());
        System.out.printf("지각: %d회\n", absenceResultDto.lateness());
        System.out.printf("결석: %d회\n", absenceResultDto.absence());

        System.out.println();

        System.out.printf("%s 대상자입니다.\n", absenceResultDto.status().getDescription());
    }

    public void printAbsenceResult(final Map<Crew, AbsenceHistory> result) {
        System.out.println("제적 위험자 조회 결과");
        result.forEach((crew, absenceResult) ->
                System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                        crew.getName(),
                        absenceResult.absence(),
                        absenceResult.lateness(),
                        absenceResult.status().getDescription()));
    }

    public void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
