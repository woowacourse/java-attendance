package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewStatistic;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.dto.AttendanceLookupDto;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.List;

public class OptionLookupAttendance extends MenuOption {
    public OptionLookupAttendance(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        super(inputView, outputView, crews, attendances);
    }

    @Override
    public void executeMenuOption(MenuCommand command) {
        executeLookupAttendanceHistory();
    }

    private void executeLookupAttendanceHistory() {
        Crew crew = findCrewByCrewName();
        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        CrewStatistic crewStatistic = new CrewStatistic(crew, crewAttendances);
        crewStatistic.checkCrewStatistic();
        AttendanceLookupDto attendanceLookupDto = AttendanceLookupDto.fromAttendanceHistoryInfo(crew, crewStatistic);
        outputView.printCrewAttendanceHistory(attendanceLookupDto);
        outputView.printCrewStatisticStatus(attendanceLookupDto);
    }
}
