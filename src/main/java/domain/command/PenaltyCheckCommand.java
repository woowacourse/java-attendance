package domain.command;

import domain.CrewDto;
import domain.Crews;
import domain.PenaltyStatus;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PenaltyCheckCommand implements Command {
    private final Crews crews;
    private final OutputView outputView;

    public PenaltyCheckCommand(Crews crews, OutputView outputView) {
        this.crews = crews;
        this.outputView = outputView;
    }

    @Override
    public void execute() {
        List<CrewDto> crewDtos = crews.createCrewDtos();
        List<CrewDto> penaltyCrewDtos = crewDtos.stream()
                .filter(crewDto -> crewDto.getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toCollection(ArrayList::new));

        outputView.printPenaltyCrews(penaltyCrewDtos);
    }
}
