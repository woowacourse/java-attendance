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
        LocalDate nowDate = getValidNowDate();
        String nickname = getValidNickname(crewHistories, nowDate);
        LocalDateTime attendanceTime = getValidAttendanceTime(nowDate);

        crewHistories.addHistory(nickname, attendanceTime);
        resultView.showAttendance(attendanceTime, campusScheduler.calculateAttendanceState(attendanceTime));
    }

    private LocalDate getValidNowDate() {
        LocalDate nowDate = LocalDate.now(clock);
        campusScheduler.validateOperationDate(nowDate);
        return nowDate;
    }

    private String getValidNickname(final CrewHistories crewHistories, final LocalDate nowDate) {
        String nickname = inputView.readNickname();
        crewHistories.validateHistoryNotExists(nickname, nowDate);
        return nickname;
    }

    private LocalDateTime getValidAttendanceTime(final LocalDate nowDate) {
        LocalDateTime attendanceTime = LocalDateTime.of(nowDate, parseAttendanceTime());
        campusScheduler.validateOperationTime(attendanceTime);
        return attendanceTime;
    }

    private LocalTime parseAttendanceTime() {
        String attendanceTime = inputView.readAttendanceTime();
        return StringParser.parseLocalTime(attendanceTime);
    }
}
