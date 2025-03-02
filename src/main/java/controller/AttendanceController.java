package controller;

import static util.Constants.*;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendancePolicy;
import domain.CrewName;
import dto.AttendanceHistory;
import dto.ModifyingResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceBook attendanceBook;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        Map<MenuOption, Runnable> menuActions = getMenuActions();
        boolean onRunning = true;
        while (onRunning) {
            MenuOption menuOption = handleWithRetry(this::getMenuOption);
            menuActions.getOrDefault(menuOption, () -> {
            }).run();
            onRunning = MenuOption.isRunningOption(menuOption);
        }
    }

    private Map<MenuOption, Runnable> getMenuActions() {
        return Map.of(
                MenuOption.ADDING_ATTENDANCE, this::addAttendance,
                MenuOption.UPDATING_ATTENDANCE, this::modifyAttendance,
                MenuOption.SHOWING_CREW_ATTENDANCE_HISTORY, this::showAttendanceHistory,
                MenuOption.SHOWING_PENALTY_CREWS, this::showPenaltyCrews
        );
    }

    private MenuOption getMenuOption() {
        String selectedMenu = inputView.readSelectedMenu();
        return MenuOption.from(selectedMenu);
    }

    private void addAttendance() {
        CrewName crewName = handleWithRetry(this::getCrewName);
        Attendance attendance = handleWithRetry(this::getAttendance);
        attendanceBook.addAttendance(crewName, attendance);
        outputView.showAttendanceResult(attendance);
    }

    private CrewName getCrewName() {
        String nameInput = inputView.readName();
        CrewName crewName = new CrewName(nameInput);
        attendanceBook.findAttendanceRecordBy(crewName);
        return crewName;
    }

    private Attendance getAttendance() {
        String timeInput = inputView.readAttendanceTime();
        return new Attendance(TODAY, LocalTime.parse(timeInput));
    }

    private void modifyAttendance() {
        CrewName crewName = handleWithRetry(this::getCrewName);
        LocalDate date = handleWithRetry(this::getDate);
        LocalTime time = handleWithRetry(this::getTime);
        Attendance attendance = new Attendance(date, time);
        ModifyingResult modifyingResult = attendanceBook.modify(crewName, attendance);
        outputView.showModifyingResult(modifyingResult);
    }

    private LocalDate getDate() {
        String dayInput = inputView.readModifyDay();
        LocalDate date = LocalDate.of(TODAY.getYear(), TODAY.getMonth(), Integer.parseInt(dayInput));
        AttendancePolicy.validateHoliday(date);
        return date;
    }

    private LocalTime getTime() {
        String timeInput = inputView.readModifyName();
        LocalTime time = LocalTime.parse(timeInput);
        AttendancePolicy.validateRunningTime(time);
        return time;
    }

    private void showAttendanceHistory() {
        AttendanceHistory attendanceHistory = handleWithRetry(this::getAttendanceHistory);
        outputView.showAttendanceHistory(attendanceHistory);
    }

    private AttendanceHistory getAttendanceHistory() {
        String nameInput = inputView.readName();
        CrewName crewName = new CrewName(nameInput);
        return attendanceBook.findAttendanceHistoryUntilYesterday(crewName);
    }

    private void showPenaltyCrews() {
        outputView.showPenaltyCrews(attendanceBook.findPenaltyCrewsSortedUntilYesterday());
    }

    private <T> T handleWithRetry(Supplier<T> task) {
        T result;
        do {
            result = handleWithErrorMessage(task);
        } while (result == null);
        return result;
    }

    private <T> T handleWithErrorMessage(Supplier<T> task) {
        try {
            return task.get();
        } catch (IllegalArgumentException e) {
            outputView.showErrorMessage(e.getMessage());
            return null;
        }
    }
}
