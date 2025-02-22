package controller.command;

import controller.AttendanceCommand;
import domain.Attendance;
import domain.AttendanceDateTime;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import error.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import util.Constants;
import view.InputView;
import view.OutputView;

public class CheckAttendanceCommand implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        final LocalDateTime fixDateTime = LocalDateTime.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH,
                Constants.FIXED_DAY, 0, 0,
                0, 0);
        final Nickname nickname = readNickname();
        final Crew crew = crews.findByNickname(nickname);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(
                LocalDateTime.of(fixDateTime.toLocalDate(), readLocalTime()));

        if (crew.isAttended(attendanceDateTime.getLocalDateTime())) {
            throw new CustomIllegalArgumentException("이미 출석했습니다. 다음에는 수정기능을 이용해주세요.");
        }

        final Attendance attendance = attend(crew, attendanceDateTime);

        OutputView.printAttendance(attendance);
    }

    private Nickname readNickname() {
        final String inputNickName = InputView.readNickName();
        return new Nickname(inputNickName);
    }

    private LocalTime readLocalTime() {
        final String inputTime = InputView.readDateTime();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        return LocalTime.parse(inputTime, formatter);
    }

    private Attendance attend(Crew crew, AttendanceDateTime attendedDateTime) {
        final Attendance attendance = new Attendance(attendedDateTime);
        crew.add(attendance);
        return attendance;
    }
}
