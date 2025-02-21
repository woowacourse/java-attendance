package controller;

import domain.AllCrew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import view.FileInputView;
import view.InputValidator;
import view.OutputView;
import view.UserInputView;

public class AttendanceSystem {
    private final FileInputView fileInputView;
    private final UserInputView userInputView;
    private final OutputView outputView;
    private final AllCrew allCrew;
    private final LocalDate date;

    public AttendanceSystem(LocalDate date) {
        this.date = date;
        this.userInputView = new UserInputView(date);
        this.outputView = new OutputView(date);
        this.fileInputView = new FileInputView();
        this.allCrew = new AllCrew();
    }

    public void run() {
        initialize();
        start();
    }

    private void initialize() {
        fileInputView.readAttendanceFile(allCrew);
        allCrew.updateAbsentHistory(date.minusDays(1));
    }

    public void start() {
        boolean onRunning = true;
        while (onRunning) {
            String menu = userInputView.askMenu();
            handleException(() -> InputValidator.validateMenu(menu));
            onRunning = executeMenu(menu);
        }
    }

    private boolean executeMenu(String menuInput) {
        if (menuInput.equals("1")) {
            checkAttendance();
        }
        if (menuInput.equals("2")) {
            modifyAttendance();
        }
        if (menuInput.equals("3")) {
            checkCrewAttendanceHistory();
        }
        if (menuInput.equals("4")) {
            checkDangerousCrew();
        }
        return !menuInput.matches("[Qq]");
    }

    private void checkAttendance() {
        String name = handleWithRetry(this::processName);
        String time = handleWithRetry(this::processTime);
        String attendanceResult = handleWithRestart(() ->
                allCrew.addCrewAttendanceByName(name,
                        LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                                Integer.parseInt(time.split(":")[0]),
                                Integer.parseInt(time.split(":")[1])))
        );
        outputView.printAttendanceResult(attendanceResult);
    }

    private String processName() {
        String name = userInputView.askNickNameForCheckAttendance();
        InputValidator.validateName(name, allCrew);
        return name;
    }

    private String processTime() {
        String time = userInputView.askAttendanceTimeForCheckAttendance();
        InputValidator.validateTimeFormat(time);
        return time;
    }

    private void modifyAttendance() {
        String modifyName = handleWithRetry(this::processModifyName);
        String day = handleWithRetry(this::processDay);
        String modifyTime = handleWithRetry(this::processModifyTime);
        String modifyResult = handleWithRestart(() ->
                allCrew.modifyCrewAttendanceByName(modifyName, LocalDateTime.of(date.getYear(),
                        date.getMonthValue(),
                        Integer.parseInt(day),
                        Integer.parseInt(modifyTime.split(":")[0]),
                        Integer.parseInt(modifyTime.split(":")[1]))
                )
        );
        outputView.printModifyAttendance(modifyResult);
    }

    private String processModifyName() {
        String name = userInputView.askNickNameForModifyAttendanceInfo();
        InputValidator.validateName(name, allCrew);
        return name;
    }

    private String processDay() {
        String day = userInputView.askDayForModifyAttendanceInfo();
        InputValidator.validateDate(day);
        return day;
    }

    private String processModifyTime() {
        String time = userInputView.askAttendanceTimeForModifyAttendance();
        InputValidator.validateTimeFormat(time);
        return time;
    }

    private void checkCrewAttendanceHistory() {
        String name = handleWithRetry(this::processName);
        outputView.printAttendanceHistory(allCrew, name);
    }

    private void checkDangerousCrew() {
        outputView.printDangerousCrew(allCrew);
    }

    private void handleException(Runnable task) {
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

