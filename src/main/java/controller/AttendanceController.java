package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        String optionInput = inputView.getOptionInput();
        MenuOption option = MenuOption.findOptionByCommand(optionInput);
    }
}
