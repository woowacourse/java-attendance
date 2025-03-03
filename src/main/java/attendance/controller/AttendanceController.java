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
import java.util.HashMap;
import java.util.Map;

public class AttendanceController {
    private static final Map<String, Runnable> operations = new HashMap<>();
    private static final String REGISTER_OPERATION_KEY = "1";
    private static final String MODIFY_OPERATION_KEY = "2";
    private static final String SHOW_ATTENDANCES_OPERATION_KEY = "3";
    private static final String SHOW_WARNING_CREWS_OPERATION_KEY = "4";
    private static final String QUIT_OPERATION_KEY = "Q";
    private final InputView inputView;
    private final ResultView resultView;

    public AttendanceController(final InputView inputView, final ResultView resultView) {

        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void run() {
        AttendanceBook attendanceBook = AttendanceLoader.load(DataSourceReader.readFile());
        initOperations(attendanceBook);
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        String inputOption;
        while (!(inputOption = inputView.readOption(today)).equals(QUIT_OPERATION_KEY)) {
            operations.get(inputOption).run();
        }
    }

    private void initOperations(AttendanceBook attendanceBook) {
        operations.put(REGISTER_OPERATION_KEY, () -> registerAttendance(attendanceBook));
        operations.put(MODIFY_OPERATION_KEY, () -> modifyAttendance(attendanceBook));
        operations.put(SHOW_ATTENDANCES_OPERATION_KEY, () -> showCrewAttendance(attendanceBook));
        operations.put(SHOW_WARNING_CREWS_OPERATION_KEY, () -> showWarningCrews(attendanceBook));
    }

    private void modifyAttendance(final AttendanceBook attendanceBook) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        String nickname = inputView.readNicknameForModify();
        int dayOfMonth = inputView.readDayForModify();
        LocalDate targetDate = LocalDate.of(2024, 12, dayOfMonth);
        LocalTime newTime = inputView.readTimeForModify();
        LocalDateTime newDateTime = LocalDateTime.of(targetDate, newTime);

        CrewAttendance crewAttendance = attendanceBook.getCrewAttendanceOf(nickname, today);
        final Attendance prevAttendance = crewAttendance.getAttendanceOn(targetDate);
        crewAttendance.modify(newDateTime);
        Attendance newAttendance = crewAttendance.getAttendanceOn(targetDate);
        resultView.printModifiedResult(prevAttendance, newAttendance);
    }

    private void registerAttendance(final AttendanceBook attendanceBook) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 0, 0);
        String nickname = inputView.readNickname();
        LocalTime attendanceTime = inputView.readAttendanceTime();
        LocalDateTime newAttendance = LocalDateTime.of(LocalDate.from(today), attendanceTime);

        attendanceBook.addAttendance(nickname, newAttendance);
        Attendance attendance = attendanceBook.getCrewAttendanceOf(nickname, today).getAttendanceOn(newAttendance);
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
