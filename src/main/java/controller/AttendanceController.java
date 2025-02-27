package controller;

import constant.Constants;
import domain.AttendanceBook;
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
        AttendanceBook attendanceBook = new AttendanceBook(AttendanceConvertor.convertToAttendances(AttendanceFileReader.readFile()));
        MenuOption option;

        do {
            this.outputView.printWelcomeMessage();
            option = InputProcessor.processInputUntilSuccess(this::processOptionInput);
            option.process(attendanceBook, Constants.NOW_DATE);
        } while (!option.equals(MenuOption.QUIT));
    }

    private MenuOption processOptionInput() {
        String optionInput = this.inputView.getOptionInput();
        return MenuOption.findOptionByCommand(optionInput);
    }
}
