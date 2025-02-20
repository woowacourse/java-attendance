package controller;

import domain.AllCrew;
import java.time.LocalDate;
import view.FileInputView;
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

    public void start() {
        fileInputView.readAttendanceFile(allCrew);
        LocalDate today = date;
        allCrew.updateAbsentHistory(today.minusDays(1));
        boolean onRunning = true;
        while (onRunning) {
            String menuInput = userInputView.askMenu();
            onRunning = executeMenu(menuInput);
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

    private void checkDangerousCrew() {
        outputView.printDangerousCrew(allCrew);
    }

    private void checkCrewAttendanceHistory() {
        String name = userInputView.askNickNameForCheckAttendanceInfo();
        outputView.printAttendanceHistory(allCrew, name);
    }

    private void checkAttendance() {
        String name = userInputView.askNickNameForCheckAttendance();
        String[] time = userInputView.askAttendanceTimeForCheckAttendance();
        outputView.printCheckedAttendance(allCrew, name, time);
    }

    private void modifyAttendance() {
        String name = userInputView.askNickNameForModifyAttendanceInfo();
        int day = userInputView.askDayForModifyAttendanceInfo();
        String[] time = userInputView.askAttendanceTimeForModifyAttendance();
        outputView.printModifyAttendance(allCrew, name, day, time);
    }
}

