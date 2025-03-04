package attendance.controller.option;

import attendance.domain.Attendances;
import attendance.domain.CrewName;
import attendance.domain.CrewNames;
import attendance.domain.CrewStatistic;
import attendance.dto.AttendanceLookupDto;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceLookupOption extends MenuOption {
    public AttendanceLookupOption(InputView inputView, CrewNames crewNames, Attendances attendances,
                                  OutputView outputView) {
        super(inputView, outputView, crewNames, attendances);
    }

    @Override
    public void execute() {
        String crewNameInput = inputView.readCrewName();
        CrewName crewName = crewNames.findCrewName(crewNameInput);
        Attendances crewAttendances = attendances.lookupCrewAttendance(crewName);
        CrewStatistic crewStatistic = new CrewStatistic(crewName);
        crewStatistic.checkAttendanceStatistic(crewAttendances);
        outputView.printAttendanceLookupResult(AttendanceLookupDto.fromCrewInformation(crewAttendances, crewStatistic));
    }
}
