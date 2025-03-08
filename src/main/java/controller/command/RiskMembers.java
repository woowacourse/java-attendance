package controller.command;

import domain.crew.Crew;
import domain.crew.Crews;
import java.util.List;
import view.OutputView;

public class RiskMembers implements AttendanceCommand {
    @Override
    public void execute(final Crews crews) {
        final List<Crew> sortedDisciplinaryCrews = crews.findSortedDisciplinaryCrews();
        OutputView.printRiskMembers(sortedDisciplinaryCrews);
    }
}
