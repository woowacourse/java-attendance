package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LocalDate localDate = LocalDate.now();
        outputView.printWelcomeMessage(localDate);

        MenuOption optionInput = InputProcessor.processInputUntilSuccess(this::processOptionInput);
    }

    private MenuOption processOptionInput() {
        String optionInput = inputView.getOptionInput();
        return MenuOption.findOptionByCommand(optionInput);
    }
}
