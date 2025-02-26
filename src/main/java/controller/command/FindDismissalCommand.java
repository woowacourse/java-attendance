package controller.command;

import domain.model.AttendanceCounter;
import domain.model.CrewHistories;
import domain.model.TodayClock;
import dto.DismissalCrewDto;
import java.util.List;
import java.util.Map;
import view.ResultView;

public class FindDismissalCommand implements Command {

    private final TodayClock clock;
    private final ResultView resultView;

    public FindDismissalCommand(final TodayClock clock, final ResultView resultView) {
        this.clock = clock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        Map<String, AttendanceCounter> dismissalCrews = crewHistories.findDismissalCrews(clock.getTodayDate());
        List<DismissalCrewDto> dtos = DismissalCrewDto.of(dismissalCrews);
        resultView.printDismissalResult(dtos);
    }
}
