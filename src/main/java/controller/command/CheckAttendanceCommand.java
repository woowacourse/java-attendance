package controller.command;

import controller.AttendanceCommandController;
import domain.Attendance;
import domain.AttendanceDateTime;
import domain.AttendanceTime;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import domain.Week;
import error.CustomIllegalArgumentException;
import view.InputView;
import view.OutputView;

public class CheckAttendanceCommand implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        Week.validateToday(AttendanceCommandController.SYSTEM_DATE_TIME);

        final Nickname nickname = readNickname();
        final Crew crew = crews.findByNickname(nickname);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.generateTodayAttendance(readLocalTime());

        if (crew.isAttended(attendanceDateTime.getLocalDateTime())) {
            throw new CustomIllegalArgumentException("이미 출석했습니다. 다음에는 수정기능을 이용해주세요.");
        }

        final Attendance attendance = attend(crew, attendanceDateTime);

        OutputView.printAttendance(attendance.getSummary());
    }

    private Nickname readNickname() {
        final String inputNickName = InputView.readNickName();
        return new Nickname(inputNickName);
    }

    private AttendanceTime readLocalTime() {
        return AttendanceTime.of(InputView.readTime());
    }

    private Attendance attend(Crew crew, AttendanceDateTime attendedDateTime) {
        final Attendance attendance = Attendance.getInstance(attendedDateTime);
        crew.add(attendance);
        return attendance;
    }
}
