package controller.command;

import domain.CrewSummary;
import domain.Crews;
import java.util.List;
import view.OutputView;

public class ConfirmationOfThoseAtRiskOfExpulsion implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        final List<CrewSummary> crewSummary = crews.getCrewSummary();
        OutputView.printAllExpulsion(crewSummary);
    }
}
