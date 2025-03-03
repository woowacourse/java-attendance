package controller.command;

import java.time.LocalDate;
import java.util.List;
import service.AttendanceService;
import view.OutputView;
import vo.DangerCrew;

public class GetPenaltyCommand implements ControllerCommand {

    private final AttendanceService service;
    private final LocalDate beginDateOfEducation;

    public GetPenaltyCommand(AttendanceService service, LocalDate beginDateOfEducation) {
        this.service = service;
        this.beginDateOfEducation = beginDateOfEducation;
    }

    @Override
    public void execute(LocalDate today) {
        LocalDate to = today.minusDays(1);

        List<DangerCrew> dangerCrews = service.findDangerCrews(beginDateOfEducation, to);
        OutputView.printDangerCrews(dangerCrews);
    }
}
