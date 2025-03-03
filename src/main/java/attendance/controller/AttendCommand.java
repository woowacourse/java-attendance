package attendance.controller;

import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.Nickname;
import attendance.util.StringParser;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendCommand implements Command {

    private final InputView inputView;
    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;

    public AttendCommand(final InputView inputView, final ResultView resultView, final Clock clock,
                         final CampusScheduler campusScheduler) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        LocalDate nowDate = LocalDate.now(clock);
        campusScheduler.validateOperationDate(nowDate);
        Nickname nickname = makeNickname();
        crewHistories.validateHistoryNotExists(nickname, nowDate);

        LocalDateTime attendanceTime = LocalDateTime.of(nowDate, parseAttendanceTime());
        campusScheduler.validateOperationTime(attendanceTime);
        crewHistories.addHistory(nickname, attendanceTime);
        resultView.showAttendance(attendanceTime, campusScheduler.calculateAttendanceState(attendanceTime));
    }

    private LocalTime parseAttendanceTime() {
        String attendanceTime = inputView.readAttendanceTime();
        return StringParser.parseLocalTime(attendanceTime);
    }

    private Nickname makeNickname() {
        String nickname = inputView.readNickname();
        return new Nickname(nickname);
    }
}
