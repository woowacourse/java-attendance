package attendance.view;

import attendance.domain.AttendanceState;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistory;
import attendance.domain.Nickname;
import attendance.domain.RiskAtExpulsion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final Map<AttendanceState, String> ATTENDANCE_STATE_KOREAN = Map.of(
            AttendanceState.ATTENDANCE, "출석",
            AttendanceState.TARDINESS, "지각",
            AttendanceState.ABSENCE, "결석"
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
    private static final String TITLE_EXPULSION_FORMAT = "%s 대상자입니다.";

    public void showBlankLine() {
        System.out.println();
    }

    public void showAttendance(final LocalDateTime attendanceTime, final AttendanceState attendanceState) {
        showBlankLine();
        System.out.printf(TITLE_ATTENDANCE, TimeFormatter.makeDateTimeMessage(attendanceTime),
                getAttendanceState(attendanceState));
    }

    public void showModifyingAttendance(final LocalDateTime previousDateTime,
                                        final AttendanceState previousAttendanceState,
                                        final LocalTime modifyingTime,
                                        final AttendanceState afterAttendanceState) {
        showBlankLine();
        System.out.printf(TITLE_MODIFYING, TimeFormatter.makeDateTimeMessage(previousDateTime),
                getAttendanceState(previousAttendanceState), TimeFormatter.makeTimeMessage(modifyingTime),
                getAttendanceState(afterAttendanceState));
    }

    public void showAttendanceHistory(final Nickname nickname, final CrewHistory crewHistory,
                                      final LocalDate nowDate, final CampusScheduler campusScheduler) {
        System.out.printf(LINE + TITLE_INQUIRY_CREW + LINE + LINE, nickname.getValue());
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
        System.out.printf(LINE + TITLE_EXPULSION_FORMAT + LINE, getRiskAtExpulsion(riskAtExpulsion));
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
