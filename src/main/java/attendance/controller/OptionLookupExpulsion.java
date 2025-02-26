package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewStatistic;
import attendance.domain.CrewStatistics;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.dto.AttendanceExpelDto;
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
        executeLookupExpulsion();
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
        AttendanceExpelDto attendanceExpelDto = AttendanceExpelDto.fromAttendanceExpelRecord(sortedCrewStatistics);
        outputView.printExpelExpectedCrews(attendanceExpelDto);
    }
}
