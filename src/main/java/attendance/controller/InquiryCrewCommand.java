package attendance.controller;

import attendance.domain.AttendanceState;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.CrewHistory;
import attendance.domain.Nickname;
import attendance.domain.RiskAtExpulsion;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

public class InquiryCrewCommand implements Command {

    private final InputView inputView;
    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;

    public InquiryCrewCommand(final InputView inputView, final ResultView resultView, final Clock clock,
                              final CampusScheduler campusScheduler) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        LocalDate nowDate = LocalDate.now(clock);
        Nickname nickname = makeNickname();
        crewHistories.validateKeyExists(nickname);
        CrewHistory history = crewHistories.findHistory(nickname);
        resultView.showAttendanceHistory(nickname, history, nowDate, campusScheduler);
        showAttendanceCountResult(history, nowDate);
    }

    private void showAttendanceCountResult(final CrewHistory history, final LocalDate nowDate) {
        Map<AttendanceState, Integer> result = campusScheduler.countByAttendanceState(
                history, nowDate);
        int attendanceCount = result.get(AttendanceState.ATTENDANCE);
        int absentCount = result.get(AttendanceState.ABSENCE);
        int lateCount = result.get(AttendanceState.TARDINESS);

        resultView.showCountByAttendanceState(attendanceCount, lateCount, absentCount);
        resultView.showExpulsion(RiskAtExpulsion.of(absentCount, lateCount));
    }

    private Nickname makeNickname() {
        String nickname = inputView.readNickname();
        return new Nickname(nickname);
    }
}
