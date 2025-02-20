package controller;

import domain.AllCrew;
import java.time.LocalDate;
import util.DateGenerator;
import view.FileInputView;
import view.OutputView;
import view.UserInputView;

public class AttendanceSystem {
    private final FileInputView fileInputView;
    private final DateGenerator dateGenerator;
    private final AllCrew allCrew;

    public AttendanceSystem(DateGenerator dateGenerator) {
        this.fileInputView = new FileInputView();
        this.dateGenerator = dateGenerator;
        this.allCrew = new AllCrew();
    }

    public void start() {
        fileInputView.readAttendanceFile(allCrew);
        LocalDate today = dateGenerator.getDate();
        allCrew.updateAbsentHistory(today.minusDays(1));
        boolean onRunning = true;
        while (onRunning) {
            String menuInput = UserInputView.askMenu();
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
        OutputView.printDangerousCrew(allCrew);
    }

    private void checkCrewAttendanceHistory() {
        String name = UserInputView.askNickNameForCheckAttendanceInfo();
        OutputView.printAttendanceHistory(allCrew, name);
    }

    private void checkAttendance() {
        String name = UserInputView.askNickNameForCheckAttendance();
        String[] time = UserInputView.askAttendanceTimeForCheckAttendance();
        OutputView.printCheckedAttendance(allCrew, name, time);
    }

    private void modifyAttendance() {
        String name = UserInputView.askNickNameForModifyAttendanceInfo();
        int day = UserInputView.askDayForModifyAttendanceInfo();
        String[] time = UserInputView.askAttendanceTimeForModifyAttendance();
        OutputView.printModifyAttendance(allCrew, name, day, time);
    }
}

