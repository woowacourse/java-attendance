package attendance.controller.command;

import attendance.domain.model.AttendanceType;
import attendance.domain.model.Campus;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewHistory;
import attendance.domain.model.TodayClock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import attendance.util.StringParser;
import attendance.util.TimeFormatter;
import attendance.view.InputView;
import attendance.view.ResultView;

public class ModifyCommand implements Command {

    private final InputView inputView;
    private final Campus campus;
    private final TodayClock todayClock;
    private final ResultView resultView;

    public ModifyCommand(final InputView inputView, final Campus campus, final TodayClock todayClock,
                         final ResultView resultView) {
        this.inputView = inputView;
        this.campus = campus;
        this.todayClock = todayClock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        CrewHistory crewHistory = crewHistories.findCrewByNickname(inputView.readModifyNickname());
        LocalDateTime modifyTime = getModifyLocalDateTime();
        campus.validateOperationTime(modifyTime);
        LocalDateTime previousTime = crewHistory.modify(modifyTime, todayClock.getTodayDate());

        resultView.printModifyHistory(TimeFormatter.formatDateTime(previousTime), AttendanceType.from(previousTime),
                TimeFormatter.formatTime(LocalTime.from(modifyTime)), AttendanceType.from(modifyTime));
    }

    private LocalDateTime getModifyLocalDateTime() {
        LocalDate modifyDate = StringParser.parseLocalDate(inputView.readModifyDay());
        campus.validateOperationDate(modifyDate);
        LocalTime modifyTime = StringParser.parseLocalTime(inputView.readModifyTime());
        return LocalDateTime.of(modifyDate, modifyTime);
    }
}
