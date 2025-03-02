package attendance.controller;

import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.Nickname;
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
        Nickname nickname = makeNickname();
        crewHistories.validateKeyExists(nickname);
        resultView.showAttendanceHistory(nickname, crewHistories, nowDate, campusScheduler);
    }

    private Nickname makeNickname() {
        String nickname = inputView.readNickname();
        return new Nickname(nickname);
    }
}
