package attendance.controller.command;

import attendance.domain.model.AttendanceStatus;
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

public class AttendCommand implements Command {

    private final InputView inputView;
    private final Campus campus;
    private final Clock clock;
    private final ResultView resultView;

    public AttendCommand(final InputView inputView, final Campus campus, final Clock clock,
                         final ResultView resultView) {
        this.inputView = inputView;
        this.campus = campus;
        this.clock = clock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        LocalDate todayDate = LocalDate.now(clock);
        campus.validateOperationDate(todayDate);
        CrewHistory crewHistory = getCrew(crewHistories);
        LocalDateTime attendanceDateTime = getLocalDateTime(todayDate);
        campus.validateOperationTime(attendanceDateTime);
        crewHistory.attend(attendanceDateTime);
        resultView.printAttendanceHistory(
                TimeFormatter.formatDateTime(attendanceDateTime), AttendanceStatus.from(attendanceDateTime));
    }

    private CrewHistory getCrew(final CrewHistories crewHistories) {
        String nickname = inputView.readNickname();
        return crewHistories.findCrewByNickname(nickname);
    }

    private LocalDateTime getLocalDateTime(final LocalDate todayDate) {
        LocalTime attendanceTime = StringParser.parseLocalTime(inputView.readAttendanceTime());
        return LocalDateTime.of(todayDate, attendanceTime);
    }
}
