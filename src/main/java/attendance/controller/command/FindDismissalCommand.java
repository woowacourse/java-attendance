package attendance.controller.command;

import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.TodayClock;
import attendance.dto.DismissalCrewDto;
import java.util.List;
import java.util.Map;
import attendance.view.ResultView;

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
