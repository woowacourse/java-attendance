package attendance.controller;

import attendance.domain.AttendanceCounter;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

public class InquiryExplusionCommand implements Command {

    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;

    public InquiryExplusionCommand(final ResultView resultView, final Clock clock,
                                   final CampusScheduler campusScheduler) {
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        LocalDate nowDate = LocalDate.now(clock);
        Map<String, AttendanceCounter> result = crewHistories.makeAttendanceCounterByNickname(nowDate, campusScheduler);
        resultView.showExpulsionCrews(result);
    }
}
