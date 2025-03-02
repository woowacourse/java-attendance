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

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;

    public AttendanceController(final InputView inputView, final ResultView resultView, final Clock clock,
                                final CampusScheduler campusScheduler) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
    }

    public void run(final CrewHistories crewHistories) {
        attend(crewHistories);
    }

    private void attend(final CrewHistories crewHistories) {
        LocalDate now = getNow();
        Nickname nickname = makeNickname();
        crewHistories.validateHistoryNotExists(nickname, now);

        LocalDateTime attendanceTime = LocalDateTime.of(now, parseAttendanceTime());
        crewHistories.addHistory(nickname, attendanceTime);
        resultView.showAttendance(attendanceTime, campusScheduler.calculateAttendanceState(attendanceTime));
    }

    private LocalDate getNow() {
        return LocalDate.now(clock);
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
