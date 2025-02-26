package controller;

import domain.AttendanceBook;
import java.time.LocalDate;
import util.AttendanceConvertor;
import util.AttendanceFileReader;
import util.InputProcessor;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LocalDate nowDate = LocalDate.now();
        AttendanceBook attendanceBook = new AttendanceBook(AttendanceConvertor.convertToAttendances(AttendanceFileReader.readFile()));
        MenuOption option;

        do {
            outputView.printWelcomeMessage(nowDate);
            option = InputProcessor.processInputUntilSuccess(this::processOptionInput);
            option.process(attendanceBook, nowDate);
        } while (!option.equals(MenuOption.QUIT));
    }

    private MenuOption processOptionInput() {
        String optionInput = inputView.getOptionInput();
        return MenuOption.findOptionByCommand(optionInput);
    }
}
