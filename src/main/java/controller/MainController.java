package controller;

import domain.Attendance;
import domain.MenuOption;
import java.time.LocalDate;
import java.util.Arrays;
import util.AttendancesFileHandler;
import util.RepeatExecutor;
import view.InputView;
import view.OutputView;

public class MainController {

    private final InputView inputView;
    private final OutputView outputView;

    public MainController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDate nowDate = LocalDate.now();
        Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), nowDate);
        RepeatExecutor.repeatUntilSuccess(this::processUntilQuitInput, outputView::printErrorMessage, nowDate, attendance);
    }

    private void processUntilQuitInput(LocalDate nowDate, Attendance attendance) {
        MenuOption menuOption;
        do {
            String option = getOptionInput(nowDate);
            menuOption = MenuOption.findByCommand(option);
            menuOption.getAttendanceController().process(attendance, nowDate);
        } while (!menuOption.equals(MenuOption.QUIT));
    }

    private String getOptionInput(LocalDate nowDate) {
        outputView.printMenuHeader(nowDate);
        return inputView.readOption(Arrays.asList(MenuOption.values()));
    }
}
