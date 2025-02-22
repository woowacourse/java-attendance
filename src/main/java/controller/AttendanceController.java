package controller;

import dto.DismissalCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import model.AttendanceType;
import model.Campus;
import model.Command;
import model.Crew;
import model.Crews;
import model.SubjectType;
import util.StringParser;
import util.TimeFormatter;
import view.InputView;
import view.ResultView;

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Campus campus;

    public AttendanceController(final InputView inputView, final ResultView resultView, final Campus campus) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.campus = campus;
    }

    public void start(final Crews crews) {
        Command command = Command.from(inputView.readCommand(getTodayDate()));
        if (command.equals(Command.QUIT)) {
            return;
        }
        process(crews, command);
        start(crews);
    }

    public static LocalDate getTodayDate() {
        return LocalDate.of(2024, 12, 13);
    }

    private void process(final Crews crews, final Command command) {
        processAttendance(crews, command);
        if (command.equals(Command.CHECK_ATTENDANCE_BY_CREW)) {
            checkAttendanceHistoryByCrew(crews);
            return;
        }
        if (command.equals(Command.CHECK_DISMISSAL_CREW)) {
            checkDismissalCrews(crews);
        }
    }

    private void processAttendance(Crews crews, Command command) {
        if (command.equals(Command.CHECK_ATTENDANCE)) {
            checkAttendance(crews);
            return;
        }
        if (command.equals(Command.MODIFY_ATTENDANCE)) {
            modifyAttendance(crews);
        }
    }

    private void checkAttendance(final Crews crews) {
        LocalDate todayDate = getTodayDate();
        campus.validateOperationDate(todayDate);
        Crew crew = getCrew(crews);
        LocalDateTime attendanceDateTime = getLocalDateTime(todayDate);
        campus.validateOperationTime(attendanceDateTime);
        crew.doAttendance(attendanceDateTime);
        resultView.printAttendanceHistory(
                TimeFormatter.formatDateTime(attendanceDateTime), AttendanceType.from(attendanceDateTime));
    }

    private LocalDateTime getLocalDateTime(final LocalDate todayDate) {
        LocalTime attendanceTime = StringParser.parseLocalTime(inputView.readAttendanceTime());
        return LocalDateTime.of(todayDate, attendanceTime);
    }

    private Crew getCrew(final Crews crews) {
        String nickname = inputView.readNickname();
        return crews.findCrewByNickname(nickname);
    }

    private void modifyAttendance(final Crews crews) {
        Crew crew = crews.findCrewByNickname(inputView.readModifyNickname());

        LocalDateTime modifyDateTime = getModifyLocalDateTime();
        campus.validateOperationTime(modifyDateTime);

        LocalDateTime previousTime = crew.doModify(modifyDateTime, getTodayDate());
        resultView.printModifyHistory(TimeFormatter.formatDateTime(previousTime), AttendanceType.from(previousTime),
                TimeFormatter.formatTime(LocalTime.from(modifyDateTime)), AttendanceType.from(modifyDateTime));
    }

    private LocalDateTime getModifyLocalDateTime() {
        LocalDate modifyDate = StringParser.parseLocalDate(inputView.readModifyDay());
        campus.validateOperationDate(modifyDate);
        LocalTime modifyTime = StringParser.parseLocalTime(inputView.readModifyTime());
        return LocalDateTime.of(modifyDate, modifyTime);
    }

    private void checkAttendanceHistoryByCrew(final Crews crews) {
        String nickname = inputView.readNickname();
        Crew crew = crews.findCrewByNickname(nickname);

        List<LocalDateTime> attendanceHistory = crew.getAttendanceHistory(getTodayDate());
        Map<AttendanceType, Integer> result = AttendanceType.countAttendanceType(attendanceHistory);
        SubjectType subjectType = SubjectType.from(result);

        resultView.printAttendanceHistoryResultByCrew(nickname, attendanceHistory, result, subjectType);
    }

    private void checkDismissalCrews(final Crews crews) {
        List<DismissalCrewDto> dtos = crews.findDismissalCrewDtos(getTodayDate());
        Collections.sort(dtos);
        resultView.printDismissalResult(dtos);
    }
}
