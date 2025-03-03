package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceLoader;
import attendance.domain.CrewAttendance;
import attendance.view.DataSourceReader;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.LocalDateTime;

public class AttendanceController {
    private final InputView inputView;
    private final ResultView resultView;

    public AttendanceController(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void run() {
        AttendanceBook attendanceBook = AttendanceLoader.load(DataSourceReader.readFile());
        String inputOption = inputView.readOption();
        if (inputOption.equals("3")) {
            showCrewAttendance(attendanceBook);
        }
    }

    private void showCrewAttendance(AttendanceBook attendanceBook) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        String nickname = inputView.readNickname();
        CrewAttendance crewAttendance = attendanceBook.getCrewAttendanceOf(nickname, today);
        resultView.printCrewAttendanceHeader(nickname);
        resultView.printCrewAttendances(crewAttendance, today);
        resultView.printAttendanceStatusCounts(crewAttendance, today);
    }
}
