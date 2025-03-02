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
    private final AttendanceBook attendanceBook;

    public AttendanceController(final ConsoleInputView inputView, final ConsoleOutputView outputView,
                                final AttendanceBook attendanceBook) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = attendanceBook;
    }

    public void run() {
        final AttendanceCommand attendanceCommand = requestAttendanceCommand();
        if (Objects.equals(attendanceCommand, AttendanceCommand.CHECK)) {
            outputView.askCrewNickName();
            final String name = inputView.readCrewName();
            attendanceBook.validateExistCrew(name);
            attendanceBook.validateExistAttendance(SystemDate.NOW.getDate(), name);
            outputView.askAttendanceTime();
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
        outputView.intro(SystemDate.NOW.getDate());
        return inputView.readAttendanceCommand();
    }

    private AttendanceDetails convertToAttendanceDetails(final AttendanceRecord attendanceRecord) {
        return AttendanceDetails.of(attendanceRecord.attendanceDate(), attendanceRecord.attendanceTime(),
                attendanceRecord.status());
    }
}
