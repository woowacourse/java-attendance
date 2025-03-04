package attendance.controller;

import attendance.domain.AttendanceCounter;
import attendance.domain.AttendanceState;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.CrewHistory;
import attendance.domain.RiskAtExpulsion;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;

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
        String nickname = inputView.readNickname();
        crewHistories.validateKeyExists(nickname);
        CrewHistory history = crewHistories.findHistory(nickname);
        resultView.showAttendanceHistory(nickname, history, nowDate, campusScheduler);
        showAttendanceCountResult(history, nowDate);
    }

    private void showAttendanceCountResult(final CrewHistory history, final LocalDate nowDate) {
        AttendanceCounter counter = campusScheduler.countByAttendanceState(
                history, nowDate);
        int attendanceCount = counter.getCount(AttendanceState.ATTENDANCE);
        int absentCount = counter.getCount(AttendanceState.ABSENCE);
        int lateCount = counter.getCount(AttendanceState.TARDINESS);

        resultView.showCountByAttendanceState(attendanceCount, lateCount, absentCount);
        resultView.showExpulsion(RiskAtExpulsion.of(absentCount, lateCount));
    }
}
