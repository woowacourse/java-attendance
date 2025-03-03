package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceLoader;
import attendance.domain.CrewAttendance;
import attendance.domain.WarningLevel;
import attendance.view.DataSourceReader;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        if (inputOption.equals("1")) {
            registerAttendance(attendanceBook);
        } else if (inputOption.equals("2")) {
            modifyAttendance(attendanceBook);
        }
        else if (inputOption.equals("3")) {
            showCrewAttendance(attendanceBook);
        } else if (inputOption.equals("4")) {
            showWarningCrews(attendanceBook);
        }
    }

    private void modifyAttendance(final AttendanceBook attendanceBook) {
        String nickname = inputView.readNicknameForModify();
        int dayOfMonth = inputView.readDayForModify();
        LocalTime newTime = inputView.readTimeForModify();
    }

    private void registerAttendance(final AttendanceBook attendanceBook) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        String nickname = inputView.readNickname();
        LocalTime attendanceTime = inputView.readAttendanceTime();
        LocalDateTime newAttendance = LocalDateTime.of(LocalDate.from(today), attendanceTime);

        attendanceBook.addAttendance(nickname, newAttendance);
        Attendance attendance = attendanceBook.getCrewAttendanceOf(nickname,today).getAttendanceOn(newAttendance);
        resultView.printAttendance(attendance);
    }

    private void showCrewAttendance(AttendanceBook attendanceBook) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        String nickname = inputView.readNickname();
        CrewAttendance crewAttendance = attendanceBook.getCrewAttendanceOf(nickname, today);
        resultView.printCrewAttendanceHeader(nickname);
        resultView.printCrewAttendances(crewAttendance, today);
        resultView.printAttendanceStatusCounts(crewAttendance, today);
        resultView.printWarningLevel(attendanceBook, nickname, today);
    }

    private void showWarningCrews(AttendanceBook attendanceBook) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        for (WarningLevel warningLevel : WarningLevel.values()) {
            warningLevel.updateCrews(attendanceBook, today);
        }
        resultView.printWarningCrews(attendanceBook, today);
    }
}
