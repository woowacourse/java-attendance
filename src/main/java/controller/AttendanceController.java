package controller;

import domain.Crews;
import domain.command.CommandHandler;
import domain.constant.StandardDate;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final String EXIT_OPTION = "Q";

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        crews.recordAllAbsence(StandardDate.DATE);

        String option = "";
        CommandHandler commandHandler = new CommandHandler(crews, inputView, outputView);

        while (!option.equals(EXIT_OPTION)) {
            outputView.printOptionMessage(StandardDate.DATE);
            option = inputView.getOption();

            commandHandler.handle(option);
        }
    }
}
