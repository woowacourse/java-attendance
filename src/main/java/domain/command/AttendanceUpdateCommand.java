package domain.command;

import domain.Attendance;
import domain.AttendanceDto;
import domain.Crew;
import domain.Crews;
import util.Converter;
import view.InputView;
import view.OutputView;

public class AttendanceUpdateCommand implements Command {
    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceUpdateCommand(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void execute() {
        Crew crew = crews.findByNickname(inputView.getEditNickname());
        Attendance attendance = crew.findByDate(Converter.convertStringToInteger(inputView.getEditDayOfMonth()));
        AttendanceDto originalAttendanceDto = attendance.toDto();
        attendance.updateAttendanceTime(Converter.convertStringToLocalTime(inputView.getNewTime()));
        AttendanceDto editedAttendanceDto = attendance.toDto();

        outputView.printUpdatedAttendanceHistory(originalAttendanceDto, editedAttendanceDto);
    }
}
