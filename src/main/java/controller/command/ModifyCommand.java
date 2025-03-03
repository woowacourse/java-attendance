package controller.command;

import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import service.AttendanceService;
import view.InputView;
import view.OutputView;
import vo.ModifyResult;

public class ModifyCommand implements ControllerCommand {

    private final AttendanceService service;

    public ModifyCommand(AttendanceService service) {
        this.service = service;
    }

    @Override
    public void execute(LocalDate today) {
        String nickName = InputView.readNickNameToModify();
        Crew crew = service.getCrewByNickName(nickName);

        String rawDateToBeModified = InputView.readDateToBeModified();
        int dayOfMonth = Integer.parseInt(rawDateToBeModified);
        LocalDate dateToBeModified = today.withDayOfMonth(dayOfMonth);

        String rawTimeToModify = InputView.readAttendTimeToModify();
        LocalTime timeToModify = LocalTime.parse(rawTimeToModify);

        ModifyResult result = service.modify(crew, dateToBeModified, timeToModify);
        OutputView.printModifyResult(result.before(), result.after());
    }
}
