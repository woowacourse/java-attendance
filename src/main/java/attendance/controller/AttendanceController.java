package attendance.controller;

import attendance.model.AttendanceBook;
import attendance.model.Function;
import attendance.util.AttendanceReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.function.Supplier;

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
        runSystem(attendanceBook);
    }

    private static void initAttendances(AttendanceBook attendanceBook) {

        AttendanceReader.initAttendances(attendanceBook);
        attendanceBook.initCrewsAbsence();
    }

    private void runSystem(final AttendanceBook attendanceBook) {

        Function function = inputFunction();
        if (function == Function.QUIT) {
            return;
        }

        doFunction(function, attendanceBook);
        runSystem(attendanceBook);
    }

    private void doFunction(final Function function, final AttendanceBook attendanceBook) {
        if (function == Function.ADD_ATTENDANCE) {

        }
        if (function == Function.MODIFY_ATTENDANCE) {

        }
        if (function == Function.GET_CREW_ATTENDANCES) {

        }
        if (function == Function.GET_EXPULSION_CANDIDATES) {

        }
    }

    private Function inputFunction() {

        return retryInput(() -> Function.getFunction(inputView.inputFunction()));
    }

    private <T> T retryInput(Supplier<T> supplier) {

        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryInput(supplier);
        }
    }
}
