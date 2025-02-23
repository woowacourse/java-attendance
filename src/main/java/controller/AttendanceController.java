package controller;

import domain.Crews;
import domain.command.CommandHandler;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        crews.recordAllAbsence();

        String option = "";
        CommandHandler commandHandler = new CommandHandler(crews, inputView, outputView);

        while (!option.equals("Q")) {
            outputView.printOptionMessage();
            option = inputView.getOption();

            commandHandler.handle(option);
        }
    }
}
