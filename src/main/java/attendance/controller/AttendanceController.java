package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceBookFactory;
import attendance.domain.Menu;
import attendance.util.AttendancesFileReader;
import attendance.util.CrewAttendancesDataParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    private final LocalDate today = LocalDate.of(2024, 21, 13);
    private AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        attendanceBook = AttendanceBookFactory.create(
                CrewAttendancesDataParser.parse(AttendancesFileReader.read()), today);
        while (true) {
            try {
                executeMenu(inputView.readSelectMenu(today));
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void executeMenu(String input) {
        Menu selectedMenu = Menu.from(input);
        if (Menu.ATTEND.equals(selectedMenu)) {

        }
        if (Menu.UPDATE_ATTENDANCE.equals(selectedMenu)) {

        }
        if (Menu.PRINT_ATTENDANCES_BY_CREW.equals(selectedMenu)) {

        }
        if (Menu.PRINT_WARNING.equals(selectedMenu)) {

        }
        if (Menu.QUIT.equals(selectedMenu)) {
            System.exit(0);
        }
    }
}
