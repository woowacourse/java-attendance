package attendance.domain;

import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.List;

public class OptionLookupAttendance extends MenuOption {
    public OptionLookupAttendance(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        super(inputView, outputView, crews, attendances);
    }

    @Override
    public void executeMenuOption(MenuCommand command) {
        if (!isCorrectCommand(command)) {
            return;
        }
        executeLookupAttendanceHistory();
    }

    @Override
    public boolean isCorrectCommand(MenuCommand command) {
        return command.equals(MenuCommand.LOOKUP);
    }

    private void executeLookupAttendanceHistory() {
        Crew crew = findCrewByCrewName();
        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);

        crewStatistic.checkCrewStatistic();

        outputView.printCrewAttendanceHistory(crew.getName(), crewStatistic.crewAttendanceHistoryInfo());
        outputView.printCrewStatisticStatus(crewStatistic.crewStatisticStatusInfo());
    }
}
