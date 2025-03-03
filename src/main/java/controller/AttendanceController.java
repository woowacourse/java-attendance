package controller;

import common.SystemDate;
import domain.AttendanceBook;
import domain.AttendanceCommand;
import domain.AttendanceModification;
import domain.AttendancePaper;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.Penalty;
import dto.AttendanceDetails;
import dto.AttendanceHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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
            final AttendancePaper attendancePaper = attendanceBook.getAttendancePaperByCrewName(name);
            attendancePaper.validateExistingAttendanceRecord(SystemDate.NOW.getDate());
            outputView.askAttendanceTime();
            final LocalTime localTime = inputView.readTime();
            final LocalDateTime localDateTime = LocalDateTime.of(SystemDate.NOW.getDate(), localTime);
            final AttendanceRecord attendanceRecord = attendancePaper.addAttendance(localDateTime);
            outputView.printAttendanceDetails(convertToAttendanceDetails(attendanceRecord));
        }
        if (Objects.equals(attendanceCommand, AttendanceCommand.MODIFY)) {
            outputView.askCrewNicknameForModification();
            final String name = inputView.readCrewName();
            final AttendancePaper attendancePaper = attendanceBook.getAttendancePaperByCrewName(name);
            outputView.askAttendanceDayForModification();
            final LocalDate localDate = inputView.readDate();
            attendancePaper.validateExistAttendanceDate(localDate);
            outputView.askAttendanceTimeForModification();
            final LocalTime localTime = inputView.readTime();
            final AttendanceTime attendanceTime = AttendanceTime.of(localDate.getDayOfWeek(), localTime);
            final AttendanceModification attendanceModification = attendancePaper.modifyAttendance(localDate, attendanceTime);
            outputView.printModifiedAttendanceDetails(
                    convertToAttendanceDetails(attendanceModification.beforeAttendanceRecord()),
                    convertToAttendanceDetails(attendanceModification.afterAttendanceRecord()));

        }
        if (Objects.equals(attendanceCommand, AttendanceCommand.LOOK_UP)) {
            outputView.askCrewNickName();
            final String name = inputView.readCrewName();
            attendanceBook.validateExistCrew(name);
            final List<AttendanceRecord> attendanceRecords = attendanceBook.lookUpAttendanceHistory(name);
            final List<AttendanceDetails> attendanceDetails = attendanceRecords.stream()
                    .map(this::convertToAttendanceDetails)
                    .toList();
            final Map<AttendanceStatus, Integer> countAttendanceStatus = attendanceBook.countAttendanceStatus(name);
            final Penalty penalty = attendanceBook.calculatePenalty(name);
            final AttendanceHistory attendanceHistory = AttendanceHistory.of(attendanceDetails, countAttendanceStatus,
                    penalty.getCode());
            outputView.printAttendanceHistory(name, attendanceHistory);
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
