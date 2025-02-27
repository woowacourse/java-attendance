package attendance.controller.command;

import attendance.domain.model.AttendanceType;
import attendance.domain.model.Campus;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewHistory;
import attendance.util.StringParser;
import attendance.util.TimeFormatter;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ModifyCommand implements Command {

    private final InputView inputView;
    private final Campus campus;
    private final Clock clock;
    private final ResultView resultView;

    public ModifyCommand(final InputView inputView, final Campus campus, final Clock clock,
                         final ResultView resultView) {
        this.inputView = inputView;
        this.campus = campus;
        this.clock = clock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        CrewHistory crewHistory = crewHistories.findCrewByNickname(inputView.readModifyNickname());
        LocalDate todayDate = LocalDate.now(clock);
        LocalDateTime modifyTime = getModifyLocalDateTime(todayDate.getYear(), todayDate.getDayOfMonth());
        campus.validateOperationTime(modifyTime);
        LocalDateTime previousTime = crewHistory.modify(modifyTime, todayDate);

        resultView.printModifyHistory(TimeFormatter.formatDateTime(previousTime), AttendanceType.from(previousTime),
                TimeFormatter.formatTime(LocalTime.from(modifyTime)), AttendanceType.from(modifyTime));
    }

    private LocalDateTime getModifyLocalDateTime(final int year, final int month) {
        LocalDate modifyDate = StringParser.parseLocalDate(year, month, inputView.readModifyDay());
        campus.validateOperationDate(modifyDate);
        LocalTime modifyTime = StringParser.parseLocalTime(inputView.readModifyTime());
        return LocalDateTime.of(modifyDate, modifyTime);
    }
}
