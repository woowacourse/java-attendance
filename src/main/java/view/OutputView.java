package view;

import static domain.AttendanceStatus.NORMAL;

import dto.AttendanceHistoriesDto;
import dto.AttendanceHistoryDto;
import dto.EditResponseDto;
import dto.ExpulsionCrewDto;
import dto.ExpulsionCrewsDto;
import java.time.LocalDateTime;
import java.util.List;
import util.DateFormatter;

public class OutputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void printAddAttendanceResult(LocalDateTime time, String result) {
        String dateFormat = DateFormatter.dateFullFormat(time);
        System.out.printf("%s (%s)\n", dateFormat, result);
        System.out.print(LINE_SEPARATOR);
    }

    public void printEditResult(EditResponseDto editResponseDto) {
        String dateFormat = DateFormatter.dateFullFormat(editResponseDto.beforeAttendanceDate(),
                editResponseDto.beforeAttendanceTime());
        LocalDateTime afterAttendanceTime = editResponseDto.afterAttendanceTime();
        String afterTime = DateFormatter.timeFormat(afterAttendanceTime.toLocalTime());
        String beforeResult = editResponseDto.beforeResult();
        String afterResult = editResponseDto.afterResult();
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!\n", dateFormat, beforeResult, afterTime,
                afterResult);
        System.out.print(LINE_SEPARATOR);
    }

    public void printAttendanceHistories(AttendanceHistoriesDto historiesDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", historiesDto.username());
        System.out.print(LINE_SEPARATOR);
        printHistories(historiesDto);
        printStatistics(historiesDto);
        printExpulsionInfo(historiesDto.status());
        System.out.print(LINE_SEPARATOR);
    }

    public void printExpulsionCrews(ExpulsionCrewsDto expulsionCrewsDto) {
        System.out.println("제적 위험자 조회 결과");
        List<ExpulsionCrewDto> crews = expulsionCrewsDto.crews();
        crews.forEach(crew -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", crew.username(), crew.absenceCount(), crew.lateCount(),
                    crew.result());
        });
        System.out.print(LINE_SEPARATOR);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
        System.out.print(LINE_SEPARATOR);
    }

    private void printHistories(AttendanceHistoriesDto historiesDto) {
        List<AttendanceHistoryDto> histories = historiesDto.histories();
        for (AttendanceHistoryDto history : histories) {
            String dateFormat = DateFormatter.dateFullFormat(history.attendanceDate(), history.attendanceTime());
            System.out.printf("%s (%s)\n", dateFormat, history.result());
        }
    }

    private void printExpulsionInfo(String result) {
        if (!result.equals(NORMAL.getStatus())) {
            System.out.printf("%s 대상자입니다.\n", result);
        }
    }

    private void printStatistics(AttendanceHistoriesDto historiesDto) {
        System.out.print(LINE_SEPARATOR);
        System.out.printf("출석: %d회\n", historiesDto.attendanceCount());
        System.out.printf("지각: %d회\n", historiesDto.lateCount());
        System.out.printf("결석: %d회\n", historiesDto.absenceCount());
        System.out.print(LINE_SEPARATOR);
    }

}
