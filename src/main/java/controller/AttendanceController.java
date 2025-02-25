package controller;

import dto.DismissalCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import model.AttendanceType;
import model.Campus;
import model.CrewHistories;
import model.CrewHistory;
import model.SubjectType;
import model.TodayClock;
import util.StringParser;
import util.TimeFormatter;
import view.Command;
import view.InputView;
import view.ResultView;

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Campus campus;
    private final TodayClock todayClock;
    private final Map<Command, Consumer<CrewHistories>> commands = initializeCommands();

    public AttendanceController(final InputView inputView, final ResultView resultView, final Campus campus,
                                final TodayClock todayClock) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.campus = campus;
        this.todayClock = todayClock;
    }

    public void start(final CrewHistories crewHistories) {
        Command command = Command.from(inputView.readCommand(getTodayDate()));
        if (command.equals(Command.QUIT)) {
            return;
        }
        process(crewHistories, command);
        start(crewHistories);
    }

    private Map<Command, Consumer<CrewHistories>> initializeCommands() {
        return Map.of(
                Command.CHECK_ATTENDANCE, this::checkAttendance,
                Command.MODIFY_ATTENDANCE, this::modifyAttendance,
                Command.CHECK_ATTENDANCE_BY_CREW, this::checkAttendanceHistoryByCrew,
                Command.CHECK_DISMISSAL_CREW, this::checkDismissalCrews
        );
    }

    private void process(final CrewHistories crewHistories, final Command command) {
        Consumer<CrewHistories> consumer = commands.get(command);
        consumer.accept(crewHistories);
    }

    private void checkAttendance(final CrewHistories crewHistories) {
        LocalDate todayDate = getTodayDate();
        campus.validateOperationDate(todayDate);
        CrewHistory crewHistory = getCrew(crewHistories);
        LocalDateTime attendanceDateTime = getLocalDateTime(todayDate);
        campus.validateOperationTime(attendanceDateTime);
        crewHistory.attend(attendanceDateTime);
        resultView.printAttendanceHistory(
                TimeFormatter.formatDateTime(attendanceDateTime), AttendanceType.from(attendanceDateTime));
    }

    private LocalDateTime getLocalDateTime(final LocalDate todayDate) {
        LocalTime attendanceTime = StringParser.parseLocalTime(inputView.readAttendanceTime());
        return LocalDateTime.of(todayDate, attendanceTime);
    }

    private CrewHistory getCrew(final CrewHistories crewHistories) {
        String nickname = inputView.readNickname();
        return crewHistories.findCrewByNickname(nickname);
    }

    private void modifyAttendance(final CrewHistories crewHistories) {
        CrewHistory crewHistory = crewHistories.findCrewByNickname(inputView.readModifyNickname());

        LocalDateTime modifyDateTime = getModifyLocalDateTime();
        campus.validateOperationTime(modifyDateTime);

        LocalDateTime previousTime = crewHistory.modify(modifyDateTime, getTodayDate());
        resultView.printModifyHistory(TimeFormatter.formatDateTime(previousTime), AttendanceType.from(previousTime),
                TimeFormatter.formatTime(LocalTime.from(modifyDateTime)), AttendanceType.from(modifyDateTime));
    }

    private LocalDateTime getModifyLocalDateTime() {
        LocalDate modifyDate = StringParser.parseLocalDate(inputView.readModifyDay());
        campus.validateOperationDate(modifyDate);
        LocalTime modifyTime = StringParser.parseLocalTime(inputView.readModifyTime());
        return LocalDateTime.of(modifyDate, modifyTime);
    }

    private void checkAttendanceHistoryByCrew(final CrewHistories crewHistories) {
        String nickname = inputView.readNickname();
        CrewHistory crewHistory = crewHistories.findCrewByNickname(nickname);

        List<LocalDateTime> attendanceHistory = crewHistory.getAttendanceHistory(getTodayDate());
        Map<AttendanceType, Integer> result = crewHistory.countAttendanceType(attendanceHistory);
        SubjectType subjectType = SubjectType.from(result);

        resultView.printAttendanceHistoryResultByCrew(nickname, attendanceHistory, result, subjectType);
    }

    private void checkDismissalCrews(final CrewHistories crewHistories) {
        List<CrewHistory> dismissalCrewHistories = crewHistories.findDismissalCrews(getTodayDate());
        List<DismissalCrewDto> dismissalCrewDtos = DismissalCrewDto.of(getTodayDate(), dismissalCrewHistories);
        Collections.sort(dismissalCrewDtos);
        resultView.printDismissalResult(dismissalCrewDtos);
    }

    private LocalDate getTodayDate() {
        return todayClock.getTodayDate();
    }
}
