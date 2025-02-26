package attendance.controller;

import attendance.model.Command;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start(LocalDate baseDate) {
        outputView.printDate(baseDate);
        String rawCommand = inputView.readCommand(Command.getCommands());
        Command command = Command.from(rawCommand);
    }
}
