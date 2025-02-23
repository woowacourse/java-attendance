package controller.command;

import domain.Crew;
import domain.Crews;
import domain.Nickname;
import view.InputView;
import view.OutputView;

public class AttendanceRecordByCrewCommand implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        Nickname nickname = readNickname();
        Crew crew = crews.findByNickname(nickname);

        OutputView.printCrewAttendances(crew.getCrewSummary(), crew.getAttendancesSummary());
    }

    private Nickname readNickname() {
        final String inputNickName = InputView.readNickName();
        return new Nickname(inputNickName);
    }
}
