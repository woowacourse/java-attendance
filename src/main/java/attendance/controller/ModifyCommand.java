package attendance.controller;

import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.util.StringParser;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ModifyCommand implements Command {

    private final InputView inputView;
    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;

    public ModifyCommand(final InputView inputView, final ResultView resultView, final Clock clock,
                         final CampusScheduler campusScheduler) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        LocalDate now = LocalDate.now(clock);

        String nickname = inputView.readModifyingNickname();
        crewHistories.validateKeyExists(nickname);

        LocalDate modifyingDate = getModifyingDate(now);
        crewHistories.validateHistoryExists(nickname, modifyingDate);
        modify(crewHistories, modifyingDate, nickname, now);
    }

    private LocalDate getModifyingDate(final LocalDate now) {
        LocalDate modifyingDate = inputView.readModifyingDay(now);
        campusScheduler.validateOperationDate(modifyingDate);
        return modifyingDate;
    }

    private void modify(final CrewHistories crewHistories, final LocalDate modifyingDate, final String nickname,
                        final LocalDate nowDate) {
        LocalTime modifyingTime = makeTime();
        LocalDateTime modifyingDateTime = LocalDateTime.of(modifyingDate, modifyingTime);
        campusScheduler.validateOperationTime(modifyingDateTime);

        LocalDateTime previousDateTime = crewHistories.modify(nickname, modifyingDateTime, nowDate);
        resultView.showModifyingAttendance(previousDateTime, campusScheduler.calculateAttendanceState(previousDateTime),
                modifyingTime, campusScheduler.calculateAttendanceState(modifyingDateTime));
    }

    private LocalTime makeTime() {
        String time = inputView.readModifyingTime();
        return StringParser.parseLocalTime(time);
    }
}
