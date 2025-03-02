package controller;

import common.SystemDate;
import domain.AttendanceBook;
import domain.AttendanceCommand;
import domain.AttendanceRecord;
import dto.AttendanceDetails;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import view.ConsoleInputView;
import view.ConsoleOutputView;

public class AttendanceController {

    private final ConsoleInputView inputView;
    private final ConsoleOutputView outputView;

    public AttendanceController(final ConsoleInputView inputView, final ConsoleOutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final AttendanceCommand attendanceCommand = requestAttendanceCommand();
        if (Objects.equals(attendanceCommand, AttendanceCommand.CHECK)) {
            final AttendanceBook attendanceBook = AttendanceBook.create();
            outputView.printAskNickName();
            final String name = inputView.readCrewName();
            attendanceBook.validateExistCrew(name);
            outputView.printAskAttendanceTime();
            final LocalTime localTime = inputView.readTime();
            final LocalDateTime localDateTime = LocalDateTime.of(SystemDate.NOW.getDate(), localTime);
            final AttendanceRecord attendanceRecord = attendanceBook.addAttendance(name, localDateTime);
            outputView.printAttendanceDetails(convertToAttendanceDetails(attendanceRecord));
        }
        if (Objects.equals(attendanceCommand, AttendanceCommand.QUIT)) {
            return;
        }
        run();
    }

    private AttendanceCommand requestAttendanceCommand() {
        outputView.printIntro(SystemDate.NOW.getDate());
        return inputView.readAttendanceCommand();
    }

    private AttendanceDetails convertToAttendanceDetails(final AttendanceRecord attendanceRecord) {
        return AttendanceDetails.of(attendanceRecord.attendanceDate(), attendanceRecord.attendanceTime(), attendanceRecord.status());
    }
}
