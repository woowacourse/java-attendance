package attendance;

import attendance.domain.Attendance;
import attendance.domain.CrewAttendanceManager;
import attendance.util.DateGenerator;
import attendance.util.DateTimeParser;
import attendance.view.InputView;
import attendance.view.Menu;
import attendance.view.OutputView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceSystem {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final CrewAttendanceManager crewAttendanceManager;

    public AttendanceSystem(final InputView inputView, final OutputView outputView, final DateGenerator dateGenerator, final CrewAttendanceManager crewAttendanceManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.dateGenerator = dateGenerator;
        this.crewAttendanceManager = crewAttendanceManager;
    }

    public void run() {
        crewAttendanceManager.initAttendanceFromFile();

        outputView.printMenu(dateGenerator.generate());
        Menu menu = inputView.readMenuCommand();

        if (menu.equals(Menu.CHECK)) {
            String nickname = inputView.readNickname();
            crewAttendanceManager.validateNicknameExists(nickname);

            String attendanceTime = inputView.readAttendanceTime();
            LocalTime time = DateTimeParser.parseTime(attendanceTime, TIME_FORMATTER);

            Attendance attendance = crewAttendanceManager.processAttendanceCheck(nickname, time);
            outputView.printAttendanceResult(attendance);
        }
    }
}
