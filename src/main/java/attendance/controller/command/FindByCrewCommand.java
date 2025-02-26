package attendance.controller.command;

import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewHistory;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FindByCrewCommand implements Command {

    private final InputView inputView;
    private final Clock clock;
    private final ResultView resultView;

    public FindByCrewCommand(final InputView inputView, final Clock clock, final ResultView resultView) {
        this.inputView = inputView;
        this.clock = clock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        String nickname = inputView.readNickname();
        CrewHistory crewHistory = crewHistories.findCrewByNickname(nickname);

        LocalDate todayDate = LocalDate.now(clock);
        List<LocalDateTime> attendanceHistory = crewHistory.getAttendanceHistory(todayDate);
        AttendanceCounter attendanceCounter = crewHistory.countAttendanceType(todayDate);

        resultView.printAttendanceHistoryResultByCrew(nickname, attendanceHistory, attendanceCounter);
    }
}
