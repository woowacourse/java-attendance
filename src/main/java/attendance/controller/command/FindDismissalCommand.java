package attendance.controller.command;

import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.CrewHistories;
import attendance.dto.DismissalCrewDto;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FindDismissalCommand implements Command {

    private final Clock clock;
    private final ResultView resultView;

    public FindDismissalCommand(final Clock clock, final ResultView resultView) {
        this.clock = clock;
        this.resultView = resultView;
    }

    @Override
    public void execute(final CrewHistories crewHistories) {
        Map<String, AttendanceCounter> dismissalCrews = crewHistories.findDismissalCrews(LocalDate.now(clock));
        List<DismissalCrewDto> dtos = DismissalCrewDto.of(dismissalCrews);
        resultView.printDismissalResult(dtos);
    }
}
