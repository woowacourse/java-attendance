package attendance.view;

import static attendance.domain.AttendanceState.ABSENCE;

import attendance.domain.AttendanceCounter;
import attendance.domain.AttendanceState;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistory;
import attendance.domain.RiskAtExpulsion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final Map<AttendanceState, String> ATTENDANCE_STATE_KOREAN = Map.of(
            AttendanceState.ATTENDANCE, "출석",
            AttendanceState.TARDINESS, "지각",
            ABSENCE, "결석"
    );
    private static final Map<RiskAtExpulsion, String> RISK_AT_EXPULSION_KOREAN = Map.of(
            RiskAtExpulsion.WARNING, "경고",
            RiskAtExpulsion.INTERVIEW, "면담",
            RiskAtExpulsion.EXPULSION, "제적"
    );

    private static final String TITLE_ATTENDANCE = "%s (%s)";
    private static final String TITLE_MODIFYING = "%s (%s) -> %s (%s) 수정 완료!";
    private static final String TITLE_INQUIRY_CREW = "이번 달 %s의 출석 기록입니다.";
    private static final String FORMAT_INQUIRY_CREW = "%s (%s)";
    private static final LocalTime DEFAULT_TIME = LocalTime.MAX;
    private static final String DEFAULT_FORMAT = "--:--";
    private static final String BLANK = " ";
    private static final String TITLE_ATTENDANCE_STATE_COUNT = """
            출석: %d회
            지각: %d회
            결석: %d회""";
    private static final String FORMAT_EXPULSION = "%s 대상자입니다.";
    private static final String TITLE_EXPULSION = "제적 위험자 조회 결과";
    private static final String FORMAT_EXPULSION_WITH_COUNT = "- %s: 결석 %d회, 지각 %d회 (%s)";

    public void showBlankLine() {
        System.out.println();
    }

    public void showAttendance(final LocalDateTime attendanceTime, final AttendanceState attendanceState) {
        showBlankLine();
        System.out.printf(TITLE_ATTENDANCE + LINE, TimeFormatter.makeDateTimeMessage(attendanceTime),
                getAttendanceState(attendanceState));
    }

    public void showModifyingAttendance(final LocalDateTime previousDateTime,
                                        final AttendanceState previousAttendanceState,
                                        final LocalTime modifyingTime,
                                        final AttendanceState afterAttendanceState) {
        showBlankLine();
        System.out.printf(TITLE_MODIFYING + LINE, TimeFormatter.makeDateTimeMessage(previousDateTime),
                getAttendanceState(previousAttendanceState), TimeFormatter.makeTimeMessage(modifyingTime),
                getAttendanceState(afterAttendanceState));
    }

    public void showAttendanceHistory(final String nickname, final CrewHistory crewHistory,
                                      final LocalDate nowDate, final CampusScheduler campusScheduler) {
        System.out.printf(LINE + TITLE_INQUIRY_CREW + LINE + LINE, nickname);
        LocalDate date = nowDate.withDayOfMonth(1);
        while (date.isBefore(nowDate)) {
            showEveryDateHistory(crewHistory, campusScheduler, date);
            date = date.plusDays(1);
        }
    }

    public void showCountByAttendanceState(final int attendanceCount, final int lateCount, final int absentCount) {
        System.out.printf(LINE + TITLE_ATTENDANCE_STATE_COUNT + LINE, attendanceCount, lateCount, absentCount);
    }

    public void showExpulsion(final RiskAtExpulsion riskAtExpulsion) {
        if (riskAtExpulsion == RiskAtExpulsion.NOT_APPLICABLE) {
            return;
        }
        System.out.printf(LINE + FORMAT_EXPULSION + LINE, getRiskAtExpulsion(riskAtExpulsion));
    }

    public void showExpulsionCrews(final Map<String, AttendanceCounter> result) {
        System.out.println(LINE + TITLE_EXPULSION);
        Map<String, AttendanceCounter> sortedResult = result.entrySet().stream()
                .sorted(makeComparator())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> y, LinkedHashMap::new));
        for (Entry<String, AttendanceCounter> entry : sortedResult.entrySet()) {
            showExpulsionCrew(entry.getKey(), entry.getValue());
        }
    }

    private Comparator<Entry<String, AttendanceCounter>> makeComparator() {
        return Comparator.comparingInt(
                        (Entry<String, AttendanceCounter> e) -> e.getValue().getCount(AttendanceState.ABSENCE) * 3
                                + e.getValue().getCount(AttendanceState.TARDINESS))
                .reversed()
                .thenComparing(e -> e.getKey());
    }

    private void showExpulsionCrew(final String nickname, final AttendanceCounter counter) {
        int absentCount = counter.getCount(ABSENCE);
        int lateCount = counter.getCount(AttendanceState.TARDINESS);
        RiskAtExpulsion riskAtExpulsion = RiskAtExpulsion.of(absentCount, lateCount);
        if (riskAtExpulsion == RiskAtExpulsion.NOT_APPLICABLE) {
            return;
        }
        System.out.printf(FORMAT_EXPULSION_WITH_COUNT + LINE, nickname, absentCount, lateCount,
                getRiskAtExpulsion(riskAtExpulsion));
    }

    private void showEveryDateHistory(final CrewHistory crewHistory,
                                      final CampusScheduler campusScheduler, LocalDate date) {
        if (campusScheduler.isNotOperationDate(date)) {
            return;
        }
        LocalDateTime history = getHistory(crewHistory, date);
        System.out.printf(FORMAT_INQUIRY_CREW + LINE, makeHistoryMessage(history),
                getAttendanceState(campusScheduler.calculateAttendanceState(history)));
    }

    private LocalDateTime getHistory(final CrewHistory crewHistory, final LocalDate date) {
        Optional<LocalDateTime> history = crewHistory.find(date);
        return history.orElseGet(() -> LocalDateTime.of(date, DEFAULT_TIME));
    }

    private String makeHistoryMessage(final LocalDateTime history) {
        LocalTime time = LocalTime.from(history);
        if (time.equals(DEFAULT_TIME)) {
            return TimeFormatter.makeDateMessage(LocalDate.from(history)) + BLANK + DEFAULT_FORMAT;
        }
        return TimeFormatter.makeDateTimeMessage(history);
    }

    private String getAttendanceState(final AttendanceState attendanceState) {
        return ATTENDANCE_STATE_KOREAN.get(attendanceState);
    }

    private String getRiskAtExpulsion(final RiskAtExpulsion riskAtExpulsion) {
        return RISK_AT_EXPULSION_KOREAN.get(riskAtExpulsion);
    }
}
