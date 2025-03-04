package attendance.controller;

import attendance.domain.AttendanceCounter;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.CrewHistory;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
        Map<String, AttendanceCounter> result = makeAttendanceCounterByNickname(crewHistories, nowDate);
        resultView.showExpulsionCrews(result);
    }

    private Map<String, AttendanceCounter> makeAttendanceCounterByNickname(final CrewHistories crewHistories,
                                                                             final LocalDate nowDate) {
        Map<String, AttendanceCounter> result = new HashMap<>();
        for (Entry<String, CrewHistory> entry : crewHistories.getHistories().entrySet()) {
            CrewHistory history = entry.getValue();
            AttendanceCounter counter = campusScheduler.countByAttendanceState(history, nowDate);
            String nickname = entry.getKey();
            result.put(nickname, counter);
        }
        return result;
    }
}
