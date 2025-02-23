package domain.command;

import domain.Crews;
import view.InputView;
import view.OutputView;

public class AttendanceHistoryCommand implements Command {
    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceHistoryCommand(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void execute() {
        String nickname = inputView.getNickname();

        outputView.printCrewAttendanceHistoryMessage(nickname);
        outputView.printAttendanceHistoryWithCrew(crews.findByNickname(nickname));
    }
}
