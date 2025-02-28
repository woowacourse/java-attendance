package controller;

import static util.Constants.*;

import domain.Attendance;
import domain.AttendanceBook;
import domain.CrewName;
import dto.AttendanceCount;
import dto.AttendanceLog;
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
        this.inputView = new InputView(attendanceBook);
        this.outputView = new OutputView();
    }

    public void run() {
        Map<MenuOption, Runnable> menuActions = getMenuActions();
        boolean onRunning = true;
        while (onRunning) {
            String selectedMenu = inputView.readSelectedMenu();
            MenuOption menuOption = MenuOption.from(selectedMenu);
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

    private void addAttendance() {
        String name = handleWithRetry(inputView::readName);
        String time = handleWithRetry(inputView::readAttendanceTime);
        CrewName crewName = new CrewName(name);
        Attendance attendance = new Attendance(TODAY, LocalTime.parse(time));
        handleWithErrorMessage(() -> attendanceBook.addAttendance(crewName, attendance));
        outputView.showAttendanceResult(attendance);
    }

    private void modifyAttendance() {
        String crewName = handleWithRetry(inputView::readModifyName);
        String day = handleWithRetry(inputView::readModifyDay);
        String time = handleWithRetry(inputView::readModifyTime);
        LocalDate date = LocalDate.of(TODAY.getYear(), TODAY.getMonth(), Integer.parseInt(day));
        ModifyingResult modifyingResult = attendanceBook.modify(
                new CrewName(crewName), new Attendance(date, LocalTime.parse(time)));
        outputView.showModifyingResult(modifyingResult);
    }

    private void showAttendanceHistory() {
        String name = handleWithRetry(inputView::readName);
        CrewName crewName = new CrewName(name);
        AttendanceLog attendanceLog = attendanceBook.findAttendanceLogUntilYesterday(crewName);
        AttendanceCount attendanceCount = attendanceBook.findCountUntilYesterday(crewName);
        outputView.showAttendanceHistory(attendanceLog, attendanceCount);
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
