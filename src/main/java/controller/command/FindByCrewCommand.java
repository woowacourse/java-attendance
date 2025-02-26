package controller.command;

import domain.model.AttendanceCounter;
import domain.model.CrewHistories;
import domain.model.CrewHistory;
import domain.model.TodayClock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import view.InputView;
import view.ResultView;

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
