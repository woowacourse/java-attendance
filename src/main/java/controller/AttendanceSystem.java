package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.MenuOption;
import dto.ModifyResult;
import java.util.function.Supplier;
import util.DateTimeManager;
import view.InputValidator;
import view.OutputView;
import view.InputView;

public class AttendanceSystem {
    private final InputView inputView;
    private final OutputView outputView;
    private final DateTimeManager dateTimeManager;
    private final AttendanceBook attendanceBook;

    public AttendanceSystem(AttendanceBook attendanceBook, DateTimeManager dateTimeManager) {
        this.dateTimeManager = dateTimeManager;
        this.inputView = new InputView(dateTimeManager.getToday());
        this.outputView = new OutputView(dateTimeManager.getToday());
        this.attendanceBook = attendanceBook;
    }

    public void run() {
        initialize();
        start();
    }

    private void initialize() {
        attendanceBook.updateAbsentHistory(dateTimeManager.getYesterday());
    }

    public void start() {
        boolean onRunning = true;
        while (onRunning) {
            String menu = inputView.askMenu();
            handleWithExceptionMessage(() -> InputValidator.validateMenu(menu));
            onRunning = MenuOption.onRunning(menu);
            executeMenu(menu);
        }
    }

    private void executeMenu(String menu) {
        if (MenuOption.isCheckAttendance(menu)) {
            checkAttendance();
        }
        if (MenuOption.isModifyAttendance(menu)) {
            modifyAttendance();
        }
        if (MenuOption.isPrintAttendanceHistory(menu)) {
            showAttendanceHistory();
        }
        if (MenuOption.isPenaltyAttendance(menu)) {
            showPenaltyCrew();
        }
    }

    private void checkAttendance() {
        String name = handleWithRetry(this::processName);
        String time = handleWithRetry(this::processTime);
        Attendance attendance = handleWithRestart(() ->
                attendanceBook.add(name,
                        dateTimeManager.getDateTime(time)));
        outputView.printAttendanceResult(attendance);
    }

    private String processName() {
        String name = inputView.askNickNameForCheckAttendance();
        InputValidator.validateName(name, attendanceBook);
        return name;
    }

    private String processTime() {
        String time = inputView.askAttendanceTimeForCheckAttendance();
        InputValidator.validateTimeFormat(time);
        return time;
    }

    private void modifyAttendance() {
        String modifyName = handleWithRetry(this::processModifyName);
        String modifyDay = handleWithRetry(this::processModifyDay);
        String modifyTime = handleWithRetry(this::processModifyTime);
        ModifyResult modifyResult = handleWithRestart(() ->
                attendanceBook.modifyCrewAttendanceByName(modifyName,
                        dateTimeManager.getModifiedDate(modifyDay, modifyTime)));
        outputView.printModifiedAttendance(modifyResult);
    }

    private String processModifyName() {
        String name = inputView.askNickNameForModifyAttendanceInfo();
        InputValidator.validateName(name, attendanceBook);
        return name;
    }

    private String processModifyDay() {
        String day = inputView.askDayForModifyAttendanceInfo();
        InputValidator.validateDate(day);
        return day;
    }

    private String processModifyTime() {
        String time = inputView.askAttendanceTimeForModifyAttendance();
        InputValidator.validateTimeFormat(time);
        return time;
    }

    private void showAttendanceHistory() {
        String name = handleWithRetry(this::processName);
        outputView.printAttendanceHistory(attendanceBook, name);
    }

    private void showPenaltyCrew() {
        outputView.printPenaltyCrew(attendanceBook);
    }

    private void handleWithExceptionMessage(Runnable task) {
        try {
            task.run();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
        }
    }

    private <T> T handleWithRetry(Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    private <T> T handleWithRestart(Supplier<T> task) {
        try {
            return task.get();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            this.start();
        }
        return null;
    }
}

