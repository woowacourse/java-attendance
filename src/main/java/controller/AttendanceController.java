package controller;

import static mapper.AttendanceMapper.toAttendanceDetails;
import static mapper.AttendanceMapper.toAttendanceDetailsGroup;
import static mapper.AttendanceMapper.toPenaltyCrews;

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
import dto.PenaltyCrew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.LoopTemplate;
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

    public void penaltyCheck() {
        final List<PenaltyCrew> penaltyCrews = toPenaltyCrews(attendanceBook.getSortedPenaltyAttendancePapers());
        outputView.printPenaltyCrews(penaltyCrews);
    }

    public void lookUpAttendance() {
        final AttendancePaper attendancePaper = requestAttendancePaper(outputView::askCrewNickName);
        final List<AttendanceDetails> attendanceDetails = toAttendanceDetailsGroup(
                attendancePaper.lookUpAttendanceHistory());
        final Map<AttendanceStatus, Integer> countAttendanceStatus = attendancePaper.countAttendanceStatus();
        final Penalty penalty = attendancePaper.calculatePenalty();
        final AttendanceHistory attendanceHistory = AttendanceHistory.of(attendanceDetails, countAttendanceStatus,
                penalty.getCode());
        outputView.printAttendanceHistory(attendancePaper.getCrewName(), attendanceHistory);
    }

    public void modifyAttendance() {
        final AttendancePaper attendancePaper = requestAttendancePaper(outputView::askCrewNicknameForModification);
        final LocalDate localDate = requestLocalDate();
        attendancePaper.validateExistAttendanceDate(localDate);
        final AttendanceTime attendanceTime = AttendanceTime.of(localDate.getDayOfWeek(),
                requestLocalTime(outputView::askAttendanceTimeForModification));
        final AttendanceModification attendanceModification = attendancePaper.modifyAttendance(localDate,
                attendanceTime);
        outputView.printModifiedAttendanceDetails(
                toAttendanceDetails(attendanceModification.beforeAttendanceRecord()),
                toAttendanceDetails(attendanceModification.afterAttendanceRecord()));
    }

    public void checkAttendance() {
        final AttendancePaper attendancePaper = requestAttendancePaper(outputView::askCrewNickName);
        attendancePaper.validateExistingAttendanceRecord(SystemDate.NOW.getDate());
        final LocalDateTime localDateTime = LocalDateTime.of(SystemDate.NOW.getDate(),
                requestLocalTime(outputView::askAttendanceTime));
        final AttendanceRecord attendanceRecord = attendancePaper.addAttendance(localDateTime);
        outputView.printAttendanceDetails(toAttendanceDetails(attendanceRecord));
    }

    public AttendanceCommand requestAttendanceCommand() {
        return LoopTemplate.tryCatchLoop(() -> {
            outputView.intro(SystemDate.NOW.getDate());
            return inputView.readAttendanceCommand();
        });
    }

    private LocalDate requestLocalDate() {
        return LoopTemplate.tryCatchLoop(() -> {
            outputView.askAttendanceDayForModification();
            return inputView.readDate();
        });
    }

    private LocalTime requestLocalTime(final Runnable outputAction) {
        return LoopTemplate.tryCatchLoop(() -> {
            outputAction.run();
            return inputView.readTime();
        });
    }

    private AttendancePaper requestAttendancePaper(final Runnable outputAction) {
        return LoopTemplate.tryCatchLoop(() -> {
            outputAction.run();
            final String name = inputView.readCrewName();
            return attendanceBook.getAttendancePaperByCrewName(name);
        });
    }
}
