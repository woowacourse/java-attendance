package attendance.controller;

import attendance.model.AttendanceBook;
import attendance.util.AttendanceReader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {

        AttendanceBook attendanceBook = new AttendanceBook();
        initAttendances(attendanceBook);
    }

    private static void initAttendances(AttendanceBook attendanceBook) {

        AttendanceReader.initAttendances(attendanceBook);
        attendanceBook.initCrewsAbsence();
    }
}
