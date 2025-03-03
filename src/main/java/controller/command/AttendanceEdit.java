package controller.command;

import domain.crew.Crew;
import domain.crew.Crews;
import domain.crew.Nickname;
import domain.dateTime.AttendanceDate;
import domain.dateTime.AttendanceDateTime;
import domain.dateTime.AttendanceTime;
import domain.record.AttendanceRecord;
import view.InputView;
import view.OutputView;

public class AttendanceEdit implements AttendanceCommand {

    @Override
    public void execute(final Crews crews) {
        final Nickname nickname = readNickname();
        final AttendanceDate attendanceDate = readAttendanceDate();
        final AttendanceTime attendanceTime = readAttendanceTime();
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(attendanceDate, attendanceTime);

        final Crew crew = crews.findByNickname(nickname);
        final AttendanceRecord beforeRecord = crew.findAttendanceRecordByDate(attendanceDate);
        final AttendanceRecord afterRecord = crew.editAttendanceDateTime(attendanceDateTime);

        OutputView.printEditAttendanceDateTime(beforeRecord, afterRecord);
    }

    private Nickname readNickname() {
        final String inputNickname = InputView.readNicknameForEdit();
        return new Nickname(inputNickname);
    }

    private AttendanceDate readAttendanceDate() {
        final String inputDate = InputView.readEditDate();
        return AttendanceDate.from(inputDate);
    }

    private AttendanceTime readAttendanceTime() {
        final String inputTime = InputView.readTimeForEdit();
        return AttendanceTime.from(inputTime);
    }
}
