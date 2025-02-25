package controller;

import dto.DismissalCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import domain.model.AttendanceCounter;
import domain.model.AttendanceType;
import domain.model.Campus;
import domain.model.CrewHistories;
import domain.model.CrewHistory;
import domain.model.TodayClock;
import util.StringParser;
import util.TimeFormatter;
import view.MenuOption;
import view.InputView;
import view.ResultView;

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Campus campus;
    private final TodayClock todayClock;
    private final Map<MenuOption, Consumer<CrewHistories>> commands = initializeCommands();

    public AttendanceController(final InputView inputView, final ResultView resultView, final Campus campus,
                                final TodayClock todayClock) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.campus = campus;
        this.todayClock = todayClock;
    }

    public void start(final CrewHistories crewHistories) {
        MenuOption menuOption = MenuOption.from(inputView.readCommand(getTodayDate()));
        if (menuOption.equals(MenuOption.QUIT)) {
            return;
        }
        process(crewHistories, menuOption);
        start(crewHistories);
    }

    private Map<MenuOption, Consumer<CrewHistories>> initializeCommands() {
        return Map.of(
                MenuOption.CHECK_ATTENDANCE, this::checkAttendance,
                MenuOption.MODIFY_ATTENDANCE, this::modifyAttendance,
                MenuOption.CHECK_ATTENDANCE_BY_CREW, this::checkAttendanceHistoryByCrew,
                MenuOption.CHECK_DISMISSAL_CREW, this::checkDismissalCrews
        );
    }

    private void process(final CrewHistories crewHistories, final MenuOption menuOption) {
        Consumer<CrewHistories> consumer = commands.get(menuOption);
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
        AttendanceCounter attendanceCounter = crewHistory.countAttendanceType(getTodayDate());
        resultView.printAttendanceHistoryResultByCrew(nickname, attendanceHistory, attendanceCounter);
    }

    private void checkDismissalCrews(final CrewHistories crewHistories) {
        Map<String, AttendanceCounter> dismissalCrews = crewHistories.findDismissalCrews(getTodayDate());
        List<DismissalCrewDto> dismissalCrewDtos = DismissalCrewDto.of(dismissalCrews);
        resultView.printDismissalResult(dismissalCrewDtos);
    }

    private LocalDate getTodayDate() {
        return todayClock.getTodayDate();
    }
}
