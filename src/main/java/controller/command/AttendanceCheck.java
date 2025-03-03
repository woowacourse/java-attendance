package controller.command;

import static controller.AttendanceCommandController.SYSTEM_DATE_TIME;

import domain.AttendanceDate;
import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.AttendanceTimePolicy;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public class AttendanceCheck implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        validateAttendanceDate();

        final Nickname nickname = readNickname();
        final Crew crew = crews.findByNickname(nickname);
        final AttendanceTime attendanceTime = readAttendanceTime();
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from(attendanceTime);

        crew.attend(attendanceDateTime);

        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateTime(
                attendanceDateTime,
                AttendanceTimePolicy.findByAttendanceDateTime(attendanceDateTime)
        );

        OutputView.printAttendanceCheck(attendanceDateTime.getDateTime(), attendanceStatus.name());
    }

    private void validateAttendanceDate() {
        final LocalDate systemDate = SYSTEM_DATE_TIME.toLocalDate();
        AttendanceDate.validate(systemDate);
    }

    private Nickname readNickname() {
        final String inputNickname = InputView.readNickname();
        return new Nickname(inputNickname);
    }

    private AttendanceTime readAttendanceTime() {
        final String inputDate = InputView.readTime();
        return AttendanceTime.from(inputDate);
    }
}
