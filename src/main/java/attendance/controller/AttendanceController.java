package attendance.controller;

import attendance.repository.AttendanceBookRepository;
import attendance.view.input.InputView;
import attendance.view.input.MenuOption;
import attendance.view.ouput.OutputView;
import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBookRepository attendanceBookRepository;

    public AttendanceController(
        final InputView inputView,
        final OutputView outputView,
        final AttendanceBookRepository attendanceBookRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBookRepository = attendanceBookRepository;
    }

    public void run(LocalDate currentDate) {
        boolean isRunning = true;

        while (isRunning) {
            isRunning = processMenu(currentDate);
        }
    }

    private boolean processMenu(final LocalDate currentDate) {
        try {
            final MenuOption menuOption = inputView.readMenuOption(currentDate);
            return menuOption != MenuOption.EXIT;
        } catch (IllegalArgumentException e) {
            outputView.printMessage(e.getMessage());
            return true;
        }
    }
}
