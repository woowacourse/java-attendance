package controller.command;

import controller.AttendanceCommandController;
import domain.DisciplinaryStatus;
import domain.crew.Crew;
import domain.crew.Crews;
import domain.crew.Nickname;
import domain.record.AttendanceRecords;
import view.InputView;
import view.OutputView;

public class AttendanceByCrew implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        final Nickname nickname = readNickname();
        final Crew crew = crews.findByNickname(nickname);
        final AttendanceRecords attendanceRecords = crew.getAttendanceRecords();
        final DisciplinaryStatus disciplinaryStatus = crew.getDisciplinaryStatus();

        OutputView.printValidAttendances(AttendanceCommandController.SYSTEM_DATE_TIME, attendanceRecords,
                disciplinaryStatus);
    }

    private Nickname readNickname() {
        final String input = InputView.readNickname();
        return new Nickname(input);
    }
}
