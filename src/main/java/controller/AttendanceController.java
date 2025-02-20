package controller;

import domain.AllCrew;
import java.time.LocalDate;
import view.FileInputView;
import view.UserInputView;
import view.OutputView;

public class AttendanceController {
    private final FileInputView fileInputView;
    AllCrew allCrew = new AllCrew();
    LocalDate today = LocalDate.of(2024, 12, 13);

    public AttendanceController(FileInputView fileInputView) {
        this.fileInputView = fileInputView;
    }

    public void run() {
        fileInputView.readAttendanceFile(allCrew);
        allCrew.updateAbsentHistory(today.minusDays(1));

        while (true) {
            String menuInput = UserInputView.showMenu();
            if (menuInput.equals("1")) {
                checkAttendance();
            }
            if (menuInput.equals("2")) {
                modifyAttendance();
            }
            if (menuInput.equals("3")) {
                checkCrewAttendanceInfo();
            }
            if (menuInput.equals("4")) {
                checkDangerousCrew();
            }
            if (menuInput.equals("Q")) {
                break;
            }
        }
    }

    private void checkDangerousCrew() {
        OutputView.printDangerousCrew(allCrew);
    }

    private void checkCrewAttendanceInfo() {
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

