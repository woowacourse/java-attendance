package controller;

import domain.AllCrew;
import java.time.LocalDate;
import java.util.regex.Pattern;
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
        while(onRunning) {
            String menu = userInputView.askMenu();
            try {
                InputValidator.validateMenu(menu);
                onRunning = executeMenu(menu);
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
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
        try {
            String name = userInputView.askNickNameForCheckAttendance();
            InputValidator.validateName(name, allCrew);
            String time = userInputView.askAttendanceTimeForCheckAttendance();
            InputValidator.validateTimeFormat(time);
            outputView.printCheckedAttendance(allCrew, name, time);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            checkAttendance();
        }
    }

    private void modifyAttendance() {
        try {
            String name = userInputView.askNickNameForModifyAttendanceInfo();
            InputValidator.validateName(name, allCrew);
            int day = userInputView.askDayForModifyAttendanceInfo();
            String time = userInputView.askAttendanceTimeForModifyAttendance();
            InputValidator.validateTimeFormat(time);
            outputView.printModifyAttendance(allCrew, name, day, time);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            modifyAttendance();
        }
    }

    private void checkCrewAttendanceHistory() {
        try {
            String name = userInputView.askNickNameForCheckAttendanceInfo();
            InputValidator.validateName(name, allCrew);
            outputView.printAttendanceHistory(allCrew, name);
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e.getMessage());
            checkCrewAttendanceHistory();
        }
    }

    private void checkDangerousCrew() {
        outputView.printDangerousCrew(allCrew);
    }
}

