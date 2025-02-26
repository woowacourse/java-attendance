package attendance.controller.command;

import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewHistory;
import attendance.domain.model.TodayClock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import attendance.view.InputView;
import attendance.view.ResultView;

public class FindByCrewCommand implements Command {

    private final InputView inputView;
    private final TodayClock todayClock;
    private final ResultView resultView;

    public FindByCrewCommand(final InputView inputView, final TodayClock todayClock, final ResultView resultView) {
        this.inputView = inputView;
        this.todayClock = todayClock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        String nickname = inputView.readNickname();
        CrewHistory crewHistory = crewHistories.findCrewByNickname(nickname);

        LocalDate todayDate = todayClock.getTodayDate();
        List<LocalDateTime> attendanceHistory = crewHistory.getAttendanceHistory(todayDate);
        AttendanceCounter attendanceCounter = crewHistory.countAttendanceType(todayDate);

        resultView.printAttendanceHistoryResultByCrew(nickname, attendanceHistory, attendanceCounter);
    }
}
