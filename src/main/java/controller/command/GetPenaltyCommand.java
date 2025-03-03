package controller.command;

import java.time.LocalDate;
import java.util.List;
import service.AttendanceService;
import view.OutputView;
import vo.DangerCrew;

public class GetPenaltyCommand implements ControllerCommand {

    private final AttendanceService service;

    public GetPenaltyCommand(AttendanceService service) {
        this.service = service;
    }

    @Override
    public void execute(LocalDate today) {
        LocalDate from = today.withDayOfMonth(1);
        LocalDate to = today.minusDays(1);

        List<DangerCrew> dangerCrews = service.findDangerCrews(from, to);
        OutputView.printDangerCrews(dangerCrews);
    }
}
