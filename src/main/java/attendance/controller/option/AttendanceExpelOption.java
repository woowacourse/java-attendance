package attendance.controller.option;

import attendance.domain.Attendances;
import attendance.domain.CrewNames;
import attendance.domain.CrewStatistics;
import attendance.dto.AttendanceExpelDto;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceExpelOption extends MenuOption {
    public AttendanceExpelOption(InputView inputView, CrewNames crewNames, Attendances attendances,
                                 OutputView outputView) {
        super(inputView, outputView, crewNames, attendances);
    }

    @Override
    public void execute() {
        CrewStatistics crewStatistics = new CrewStatistics();
        CrewStatistics crewStatisticsResult = crewStatistics.generateCrewStatistics(crewNames, attendances);
        outputView.printAttendanceExpelResult(AttendanceExpelDto.fromCrewStatistics(crewStatisticsResult));
    }
}
