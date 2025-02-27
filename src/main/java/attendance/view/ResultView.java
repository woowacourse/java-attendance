package attendance.view;

import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.AttendanceType;
import attendance.domain.model.SubjectType;
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
    private static final String ATTENDANCE_TYPE_COUNT_FORM = """
            출석: %d회
            지각: %d회
            결석: %d회
            """;
    private static final String SUBJECT_TYPE_FORM = "%s 대상자입니다.";
    private static final String DISMISSAL_RESULT_TITLE = "제적 위험자 조회 결과";
    private static final Comparator<DismissalCrewDto> COMPARATOR =
            Comparator.comparing(DismissalCrewDto::subjectType, SubjectType.getComparator())
                    .thenComparing(dto -> SubjectType.calculateTotalLateCount(dto.lateCount(), dto.absentCount()),
                            Comparator.reverseOrder())
                    .thenComparing(DismissalCrewDto::nickname);
    private static final String DISMISSAL_RESULT_FORM = "- %s: 결석 %d회, 지각 %d회 (%s)";
    private static Map<SubjectType, String> SUBJECT_TYPE_KOREAN = Map.of(
            SubjectType.WARNING, "경고",
            SubjectType.INTERVIEW, "면담",
            SubjectType.EXPULSION, "제적",
            SubjectType.NOT_APPLICABLE, "해당없음"
    );
    private static Map<AttendanceType, String> ATTENDANCE_TYPE_KOREAN = Map.of(
            AttendanceType.ATTENDANCE, "출석",
            AttendanceType.LATE, "지각",
            AttendanceType.ABSENCE, "결석"
    );

    public void printAttendanceHistory(final String attendanceTime, final AttendanceType attendanceType) {
        System.out.printf(ATTENDANCE_HISTORY_FORM + LINE, attendanceTime, ATTENDANCE_TYPE_KOREAN.get(attendanceType));
    }

    public void printModifyHistory(final String previousTime, final AttendanceType previousType,
                                   final String modifyTime, final AttendanceType modifyType) {
        System.out.printf(LINE + MODIFY_HISTORY_FORM + LINE, previousTime, ATTENDANCE_TYPE_KOREAN.get(previousType),
                modifyTime, ATTENDANCE_TYPE_KOREAN.get(modifyType));
    }

    public void printAttendanceHistoryResultByCrew(
            final String nickname,
            final List<LocalDateTime> attendanceHistory,
            final AttendanceCounter attendanceCounter
    ) {
        System.out.printf(LINE + ATTENDANCE_HISTORY_BY_CREW_FORM + LINE + LINE, nickname);
        printAttendanceHistories(attendanceHistory);
        printAttendanceTypeCount(attendanceCounter);
        printSubjectType(SubjectType.from(attendanceCounter.getAbsentCount(), attendanceCounter.getLateCount()));
    }

    public void printDismissalResult(final List<DismissalCrewDto> dtos) {
        System.out.println(DISMISSAL_RESULT_TITLE);
        dtos.stream()
                .sorted(COMPARATOR)
                .map(dto -> String.format(DISMISSAL_RESULT_FORM, dto.nickname(),
                        dto.absentCount(), dto.lateCount(), SUBJECT_TYPE_KOREAN.get(dto.subjectType())))
                .forEach(System.out::println);
    }

    private void printAttendanceHistories(final List<LocalDateTime> attendanceHistory) {
        attendanceHistory.forEach(localDateTime -> printAttendanceHistory(
                TimeFormatter.formatDateTime(localDateTime),
                AttendanceType.from(localDateTime)));
    }

    private void printAttendanceTypeCount(final AttendanceCounter attendanceCounter) {
        System.out.printf(LINE + ATTENDANCE_TYPE_COUNT_FORM + LINE,
                attendanceCounter.getAttendanceCount(),
                attendanceCounter.getLateCount(),
                attendanceCounter.getAbsentCount()
        );
    }

    private void printSubjectType(final SubjectType subjectType) {
        if (subjectType.equals(SubjectType.NOT_APPLICABLE)) {
            return;
        }
        System.out.printf(SUBJECT_TYPE_FORM + LINE, SUBJECT_TYPE_KOREAN.get(subjectType));
    }
}
