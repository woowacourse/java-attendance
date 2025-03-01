package controller.command;

import domain.AttendanceDateTime;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.util.List;
import service.AttendanceService;
import view.InputView;
import view.OutputView;
import vo.AttendanceStatusCount;

public class GetRecordsCommand implements ControllerCommand {

    private final AttendanceService service;

    public GetRecordsCommand(AttendanceService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        String nickName = InputView.readNickName();
        Crew crew = service.getCrewByNickName(nickName);

        LocalDate from = LocalDate.now().withDayOfMonth(1);
        LocalDate to = LocalDate.now().minusDays(1);

        List<AttendanceDateTime> attendances = service.findAllRecordsByCrewBetween(crew, from, to);
        OutputView.printAttendanceList(crew, attendances);

        AttendanceStatusCount counts = service.countAttendanceStatusesByCrewBetween(crew, from, to);
        Penalty penalty = Penalty.determine(counts.late(), counts.absence());

        OutputView.printCountAndPenalty(counts.onTime(), counts.late(), counts.absence(), penalty);
    }
}
