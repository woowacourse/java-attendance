package attendance.view;

import attendance.domain.AttendanceState;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.Nickname;
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
    private static final String TITLE_ATTENDANCE = "%s (%s)";
    private static final String TITLE_MODIFYING = "%s (%s) -> %s (%s) 수정 완료!";
    private static final String TITLE_INQUIRY_CREW = "이번 달 %s의 출석 기록입니다.";
    private static final String FORMAT_INQUIRY_CREW = "%s (%s)";
    private static final LocalTime DEFAULT_TIME = LocalTime.MAX;
    private static final String DEFAULT_FORMAT = "--:--";
    private static final String BLANK = " ";

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

    public void showAttendanceHistory(final Nickname nickname, final CrewHistories crewHistories,
                                      final LocalDate nowDate, final CampusScheduler campusScheduler) {
        System.out.printf(TITLE_INQUIRY_CREW + LINE + LINE, nickname.getValue());
        LocalDate date = nowDate.withDayOfMonth(1);
        while (date.isBefore(nowDate)) {
            showEveryDateHistory(nickname, crewHistories, campusScheduler, date);
            date = date.plusDays(1);
        }
    }

    private void showEveryDateHistory(final Nickname nickname, final CrewHistories crewHistories,
                                      final CampusScheduler campusScheduler, LocalDate date) {
        if (campusScheduler.isNotOperationDate(date)) {
            return;
        }
        LocalDateTime history = getHistory(nickname, crewHistories, date);
        System.out.printf(FORMAT_INQUIRY_CREW + LINE, makeHistoryMessage(history),
                getAttendanceState(campusScheduler.calculateAttendanceState(history)));
    }

    private LocalDateTime getHistory(final Nickname nickname, final CrewHistories crewHistories,
                                     final LocalDate date) {
        Optional<LocalDateTime> history = crewHistories.findHistory(nickname, date);
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
}
