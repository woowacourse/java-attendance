package controller.command;

import controller.AttendanceCommand;
import domain.Crews;
import view.OutputView;

public class ConfirmationOfThoseAtRiskOfExpulsion implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        OutputView.printAllExpulsion(crews);
    }
}
