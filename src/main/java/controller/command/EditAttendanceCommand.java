package controller.command;

import domain.Attendance;
import domain.AttendanceDateTime;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import view.InputView;
import view.OutputView;

public class EditAttendanceCommand implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        final Nickname nickname = readNicknameForEditAttendance();
        final Crew crew = crews.findByNickname(nickname);
        final AttendanceDateTime desiredUpdateDateTime = readUpdateDateTime();
        final Attendance oldAttendance = crew.getAttendance(desiredUpdateDateTime);
        final Attendance newAttendance = Attendance.getInstance(desiredUpdateDateTime);

        crew.updateAttendance(oldAttendance, newAttendance);
        OutputView.printUpdateAttendance(oldAttendance.getSummary(), newAttendance.getSummary());
    }

    private Nickname readNicknameForEditAttendance() {
        final String inputNickName = InputView.readUpdateNickName();
        return new Nickname(inputNickName);
    }

    private AttendanceDateTime readUpdateDateTime() {
        final String desiredUpdateDate = InputView.readUpdateDate();
        final String desiredUpdateTime = InputView.readUpdateDateTime();
        return AttendanceDateTime.of(desiredUpdateDate, desiredUpdateTime);
    }
}
