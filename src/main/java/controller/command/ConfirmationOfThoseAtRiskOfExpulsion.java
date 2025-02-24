package controller.command;

import domain.CrewDto;
import domain.Crews;
import java.util.List;
import view.OutputView;

public class ConfirmationOfThoseAtRiskOfExpulsion implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        final List<CrewDto> crewDtos = crews.getSortedCrews()
                .stream()
                .map(CrewDto::from)
                .toList();
        OutputView.printAllExpulsion(crewDtos);
    }
}
