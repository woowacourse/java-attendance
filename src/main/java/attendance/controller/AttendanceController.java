package attendance.controller;

import attendance.controller.util.DateTimeConverter;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceBookFactory;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceTime;
import attendance.domain.Menu;
import attendance.dto.AttendanceResultResponse;
import attendance.util.AttendancesFileReader;
import attendance.util.CrewAttendancesDataParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LocalDate today;
    private AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView, LocalDate today) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.today = today;
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
            attend();
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

    private void attend() {
        AttendanceDate attendanceDate = AttendanceDate.from(today);

        String inputTime = inputView.readAttendTime();
        LocalTime time = DateTimeConverter.convertToTime(inputTime);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        Attendance attendance = new Attendance(attendanceDate, attendanceTime);
        attendanceBook.attend(inputView.readAttendNickname(), attendance);

        outputView.printAttendResult(AttendanceResultResponse.from(attendance));
    }
}
