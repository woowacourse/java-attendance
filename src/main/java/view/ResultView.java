package view;

import dto.AttendanceHistoryDto;
import dto.DismissalCrewDto;
import java.util.List;
import model.AttendanceType;
import model.AttendanceTypeCountResult;
import model.DismissalType;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final String ATTENDANCE_MODIFY_RESULT_FORMAT = "%s -> %s 수정 완료!";
    private static final String ATTENDANCE_HISTORY_RESULT_FORMAT = "이번 달 %s의 출석 기록입니다.";
    private static final String ATTENDANCE_TYPE_COUNT_RESULT_FORMAT = "%s: %d회";
    private static final String DISMISSAL_TYPE_FORMAT = "%s 대상자입니다.";
    private static final String ALL_DISMISSAL_CREW_RESULT_TITLE = "제적 위험자 조회 결과";
    private static final String DISMISSAL_CREW_RESULT_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)";

    public void printAttendanceCheckResult(final AttendanceHistoryDto dto) {
        System.out.println();
        printAttendanceHistoryDto(dto);
    }

    public void printAttendanceModifyResult(
            final AttendanceHistoryDto oldDto,
            final AttendanceHistoryDto newDto
    ) {
        System.out.printf(LINE + ATTENDANCE_MODIFY_RESULT_FORMAT + LINE,
                oldDto.toString(), newDto.toStringWithoutDate());
    }

    public void printAttendanceHistory(final String nickname, final List<AttendanceHistoryDto> dtos) {
        System.out.printf(LINE + ATTENDANCE_HISTORY_RESULT_FORMAT + LINE + LINE, nickname);
        dtos.forEach(this::printAttendanceHistoryDto);
        System.out.println();
    }

    public void printAttendanceTypeCountResult(final AttendanceTypeCountResult attendanceTypeCountResult) {
        attendanceTypeCountResult.typeResult().entrySet().forEach(
                entry -> System.out.printf(ATTENDANCE_TYPE_COUNT_RESULT_FORMAT + LINE,
                        entry.getKey().getName(),
                        entry.getValue())
        );
        System.out.println();
    }

    public void printDismissalType(final DismissalType dismissalType) {
        System.out.printf(DISMISSAL_TYPE_FORMAT + LINE, dismissalType.getName());
    }

    public void printAllDismissalCrew(final List<DismissalCrewDto> allDismissalCrewByOrder) {
        System.out.println(LINE + ALL_DISMISSAL_CREW_RESULT_TITLE);
        allDismissalCrewByOrder.stream()
                .filter(dto -> dto.dismissalType() != DismissalType.NONE)
                .forEach(dto -> System.out.printf(DISMISSAL_CREW_RESULT_FORMAT + LINE,
                        dto.nickname(),
                        dto.typeCountResult().get(AttendanceType.ABSENT),
                        dto.typeCountResult().get(AttendanceType.LATE),
                        dto.dismissalType().getName())
                );
    }

    private void printAttendanceHistoryDto(final AttendanceHistoryDto dto) {
        System.out.println(dto.toString());
    }
}
