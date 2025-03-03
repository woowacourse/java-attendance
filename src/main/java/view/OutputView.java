package view;

import domain.AttendanceDate;
import domain.AttendanceState;
import domain.Calender;
import domain.Crew;
import domain.Dismissal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

public class OutputView {

    private static final String ATTENDANCE_RECORD_FORMATTER = "\n%02d월 %02d일 %s %02d:%02d (%s)";
    private static final String UPDATE_ATTENDANCE_RESULT_FORMATTER = "\n%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!";
    private static final String ATTENDANCE_HISTORY_INFO_FORMATTER = "\n이번 달 %s의 출석 기록입니다.";
    private static final String HISTORY_FORMATTER = "%02d월 %02d일 %s %02d:%02d (%s)";
    private static final String HISTORY_ABSENCE_FORMAT = "%02d월 %02d일 %s --:-- (%s)";
    private static final String STATE_COUNT_FORMATTER = "%s: %d회";
    private static final String DISMISSAL_FORMATTER = "\n%s 대상자입니다.";
    private static final String ABSENCE_RESULT_FORMATTER = "- %s: %s %d회, %s %d회, (%s)";
    private static final String ABSENCE_INFO_MESSAGE = "제적 위험자 조회 결과";
    private static final String EXIT_MESSAGE = "프로그램을 종료합니다.";
    private static final String NEXT_LINE = System.lineSeparator();

    public void printWellComeMessage(final AttendanceDate dateProvider) {
        String wellComeMessageFormat = getWellComeMessageFormat(dateProvider);
        System.out.println(wellComeMessageFormat);
    }

    private String getWellComeMessageFormat(AttendanceDate dateProvider) {
        return String.format(
                "\n오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.",
                dateProvider.getMonth(),
                dateProvider.getDayOfMonth(),
                dateProvider.getDayOfWeek().getDescription());
    }

    public void printAttendanceRecord(final LocalDateTime attendanceDateTime, final AttendanceState attendanceState) {
        String attendanceRecordFormat = getAttendanceRecordFormat(attendanceDateTime, attendanceState);
        System.out.println(attendanceRecordFormat);
    }

    private String getAttendanceRecordFormat(LocalDateTime attendanceDateTime, AttendanceState attendanceState) {
        return String.format
                (ATTENDANCE_RECORD_FORMATTER,
                        attendanceDateTime.getMonth().getValue(),
                        attendanceDateTime.getDayOfMonth(),
                        Calender.findBy(attendanceDateTime).getDescription(),
                        attendanceDateTime.getHour(),
                        attendanceDateTime.getMinute(),
                        attendanceState.getState());
    }

    public void printUpdateAttendanceRecord(final LocalDateTime beforeAttendance,
                                            final AttendanceState beforeAttendanceState,
                                            final LocalDateTime afterAttendance,
                                            final AttendanceState afterAttendanceState) {
        String updateAttendanceResultFormat = getUpdateAttendanceResultFormat(
                beforeAttendance,
                beforeAttendanceState,
                afterAttendance,
                afterAttendanceState);

        System.out.println(updateAttendanceResultFormat);
    }

    private String getUpdateAttendanceResultFormat(LocalDateTime beforeAttendance,
                                                   AttendanceState beforeAttendanceState,
                                                   LocalDateTime afterAttendance,
                                                   AttendanceState afterAttendanceState) {
        return String.format(
                UPDATE_ATTENDANCE_RESULT_FORMATTER,
                beforeAttendance.getMonthValue(),
                beforeAttendance.getDayOfMonth(),
                Calender.findBy(beforeAttendance).getDescription(),
                beforeAttendance.getHour(),
                beforeAttendance.getMinute(),
                beforeAttendanceState.getState(),
                afterAttendance.getHour(),
                afterAttendance.getMinute(),
                afterAttendanceState.getState());
    }

    public void printAttendanceHistory(final String name, final Map<LocalDateTime, AttendanceState> history) {
        StringJoiner sj = new StringJoiner(NEXT_LINE);
        String attendanceHistoryInfoFormat = String.format(ATTENDANCE_HISTORY_INFO_FORMATTER, name);

        history.entrySet().stream()
                .map(this::createHistoryFormat)
                .forEach(sj::add);

        System.out.println(attendanceHistoryInfoFormat);
        System.out.println(sj);
    }

    private String createHistoryFormat(Entry<LocalDateTime, AttendanceState> attendance) {
        LocalDateTime localDateTime = attendance.getKey();

        if (isNotingTime(localDateTime)) {
            return getHistoryFormat(attendance, localDateTime);
        }
        return getHistoryAbsenceFormat(attendance, localDateTime);
    }

    private boolean isNotingTime(LocalDateTime localDateTime) {
        return localDateTime.getHour() != 0;
    }

    private String getHistoryFormat(Entry<LocalDateTime, AttendanceState> attendance, LocalDateTime localDateTime) {
        return String.format(HISTORY_FORMATTER, localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                Calender.findBy(localDateTime).getDescription(),
                localDateTime.getHour(),
                localDateTime.getMinute(),
                attendance.getValue().getState());
    }

    private String getHistoryAbsenceFormat(Entry<LocalDateTime, AttendanceState> attendance, LocalDateTime key) {
        return String.format(
                HISTORY_ABSENCE_FORMAT,
                key.getMonthValue(),
                key.getDayOfMonth(),
                Calender.findBy(key).getDescription(),
                attendance.getValue().getState());
    }

    public void printAttendanceStateCounts(final Map<AttendanceState, Integer> attendanceStateCounts,
                                           final Dismissal dismissal) {
        StringJoiner sj = new StringJoiner(NEXT_LINE);

        for (Entry<AttendanceState, Integer> attendanceState : attendanceStateCounts.entrySet()) {
            AttendanceState state = attendanceState.getKey();
            String stateCountFormat = getStateCountFormat(attendanceState, state);
            sj.add(stateCountFormat);
        }
        String dismissalFormat = getDismissalFormat(dismissal);
        sj.add(dismissalFormat);

        System.out.println(NEXT_LINE + sj);
    }

    private String getDismissalFormat(Dismissal dismissal) {
        return String.format(
                DISMISSAL_FORMATTER,
                dismissal.getDescription());
    }

    private String getStateCountFormat(Entry<AttendanceState, Integer> attendanceState, AttendanceState state) {
        return String.format(
                STATE_COUNT_FORMATTER,
                state.getState(),
                attendanceState.getValue());
    }

    public void printAbsenceRecord(final Map<Crew, Map<AttendanceState, Integer>> absenceRecord) {
        StringJoiner sj = new StringJoiner(NEXT_LINE);

        for (Entry<Crew, Map<AttendanceState, Integer>> absence : absenceRecord.entrySet()) {
            Crew crew = absence.getKey();
            Map<AttendanceState, Integer> attendanceStateCount = absence.getValue();
            String absenceResultFormat = getAbsenceResultFormat(attendanceStateCount, crew);
            sj.add(absenceResultFormat);
        }
        System.out.println(ABSENCE_INFO_MESSAGE);
        System.out.println(NEXT_LINE + sj);
    }

    private String getAbsenceResultFormat(Map<AttendanceState, Integer> attendanceStateCount, Crew crew) {
        Integer absenceCount = attendanceStateCount.get(AttendanceState.ABSENCE);
        Integer lateCount = attendanceStateCount.get(AttendanceState.LATE);
        return String.format(
                ABSENCE_RESULT_FORMATTER,
                crew.getName(),
                AttendanceState.ABSENCE.getState(),
                absenceCount, AttendanceState.LATE.getState(),
                lateCount,
                Dismissal.findDismissalBy(lateCount, absenceCount).getDescription());
    }

    public void printExit() {
        System.out.println(EXIT_MESSAGE);
    }

    public void printAlreadyAttendance(String message) {
        System.out.println(message);
    }
}
