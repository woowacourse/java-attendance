package attendance.view;

import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.AttendanceStatus;
import attendance.domain.model.WarningLevel;
import attendance.dto.DismissalCrewDto;
import attendance.util.TimeFormatter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final String ATTENDANCE_HISTORY_FORM = "%s (%s)";
    private static final String MODIFY_HISTORY_FORM = "%s (%s) -> %s (%s) 수정 완료!";
    private static final String ATTENDANCE_HISTORY_BY_CREW_FORM = "이번 달 %s의 출석 기록입니다.";
    private static final String ATTENDANCE_STATUS_COUNT_FORM = """
            출석: %d회
            지각: %d회
            결석: %d회
            """;
    private static final String WARNING_LEVEL_FORM = "%s 대상자입니다.";
    private static final String DISMISSAL_RESULT_TITLE = "제적 위험자 조회 결과";
    private static final Comparator<DismissalCrewDto> COMPARATOR =
            Comparator.comparing(DismissalCrewDto::warningLevel, WarningLevel.getComparator())
                    .thenComparing(dto -> WarningLevel.calculateTotalLateCount(dto.lateCount(), dto.absentCount()),
                            Comparator.reverseOrder())
                    .thenComparing(DismissalCrewDto::nickname);
    private static final String DISMISSAL_RESULT_FORM = "- %s: 결석 %d회, 지각 %d회 (%s)";
    private static Map<WarningLevel, String> WARNING_LEVEL_KOREAN = Map.of(
            WarningLevel.WARNING, "경고",
            WarningLevel.INTERVIEW, "면담",
            WarningLevel.EXPULSION, "제적",
            WarningLevel.NOT_APPLICABLE, "해당없음"
    );
    private static Map<AttendanceStatus, String> ATTENDANCE_STATUS_KOREAN = Map.of(
            AttendanceStatus.ATTENDANCE, "출석",
            AttendanceStatus.LATE, "지각",
            AttendanceStatus.ABSENCE, "결석"
    );

    public void printAttendanceHistory(final String attendanceTime, final AttendanceStatus attendanceStatus) {
        System.out.printf(ATTENDANCE_HISTORY_FORM + LINE, attendanceTime,
                ATTENDANCE_STATUS_KOREAN.get(attendanceStatus));
    }

    public void printModifyHistory(final String previousTime, final AttendanceStatus previousStatus,
                                   final String modifyTime, final AttendanceStatus modifyStatus) {
        System.out.printf(LINE + MODIFY_HISTORY_FORM + LINE, previousTime, ATTENDANCE_STATUS_KOREAN.get(previousStatus),
                modifyTime, ATTENDANCE_STATUS_KOREAN.get(modifyStatus));
    }

    public void printAttendanceHistoryResultByCrew(
            final String nickname,
            final List<LocalDateTime> attendanceHistory,
            final AttendanceCounter attendanceCounter
    ) {
        System.out.printf(LINE + ATTENDANCE_HISTORY_BY_CREW_FORM + LINE + LINE, nickname);
        printAttendanceHistories(attendanceHistory);
        printWarningLevelCount(attendanceCounter);
        printWarningLevel(WarningLevel.from(attendanceCounter.getAbsentCount(), attendanceCounter.getLateCount()));
    }

    public void printDismissalResult(final List<DismissalCrewDto> dtos) {
        System.out.println(DISMISSAL_RESULT_TITLE);
        dtos.stream()
                .sorted(COMPARATOR)
                .map(dto -> String.format(DISMISSAL_RESULT_FORM, dto.nickname(),
                        dto.absentCount(), dto.lateCount(), WARNING_LEVEL_KOREAN.get(dto.warningLevel())))
                .forEach(System.out::println);
    }

    private void printAttendanceHistories(final List<LocalDateTime> attendanceHistory) {
        attendanceHistory.forEach(localDateTime -> printAttendanceHistory(
                TimeFormatter.formatDateTime(localDateTime),
                AttendanceStatus.from(localDateTime)));
    }

    private void printWarningLevelCount(final AttendanceCounter attendanceCounter) {
        System.out.printf(LINE + ATTENDANCE_STATUS_COUNT_FORM + LINE,
                attendanceCounter.getAttendanceCount(),
                attendanceCounter.getLateCount(),
                attendanceCounter.getAbsentCount()
        );
    }

    private void printWarningLevel(final WarningLevel warningLevel) {
        if (warningLevel.equals(WarningLevel.NOT_APPLICABLE)) {
            return;
        }
        System.out.printf(WARNING_LEVEL_FORM + LINE, WARNING_LEVEL_KOREAN.get(warningLevel));
    }
}
