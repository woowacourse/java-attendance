package attendance.domain;

import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.ArrayList;
import java.util.List;

public class OptionLookupExpulsion extends MenuOption {
    public OptionLookupExpulsion(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        super(inputView, outputView, crews, attendances);
    }

    @Override
    public void executeMenuOption(MenuCommand command) {
        if (!isCorrectCommand(command)) {
            return;
        }
        executeLookupExpulsion();
    }

    @Override
    public boolean isCorrectCommand(MenuCommand command) {
        return command.equals(MenuCommand.EXPEL);
    }

    private void executeLookupExpulsion() {
        List<CrewStatistic> crewsStatistics = new ArrayList<>();
        for (Crew crew : crews.getCrews()) {
            List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
            CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);
            crewStatistic.checkCrewStatistic();
            crewsStatistics.add(crewStatistic);
        }
        CrewStatistics crewStatistics = new CrewStatistics(crewsStatistics);
        CrewStatistics sortedCrewStatistics = crewStatistics.sortCrewStatistics();
        InfoLookupExpulsion infoLookupExpulsion = new InfoLookupExpulsion(
                sortedCrewStatistics.crewsExpelExpectedInfo());
        infoLookupExpulsion.createCrewExpelRecords();
        outputView.printExpelExpectedCrews(infoLookupExpulsion);
    }
}
