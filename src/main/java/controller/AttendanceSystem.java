package controller;

import domain.AllCrew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import view.FileInputView;
import view.OutputView;
import view.UserInputView;

import static constant.MenuOption.*;

public class AttendanceSystem {
    private final FileInputView fileInputView;
    private final UserInputView userInputView;
    private final OutputView outputView;
    private final AllCrew allCrew;
    private final LocalDate todayDate;

    private static final Pattern quitPattern = Pattern.compile(QUIT_REGEX.getValue());

    public AttendanceSystem(LocalDate todayDate) {
        this.todayDate = todayDate;
        this.userInputView = new UserInputView(todayDate);
        this.outputView = new OutputView(todayDate);
        this.fileInputView = new FileInputView();
        this.allCrew = new AllCrew();
    }

    public void start() {
        fileInputView.readAttendanceFile(allCrew);
        allCrew.updateAbsentHistory(todayDate.minusDays(1));
        boolean onRunning = true;
        while (onRunning) {
            String menuInput = userInputView.askMenu();
            onRunning = executeMenu(menuInput);
        }
    }

    private boolean executeMenu(String menuInput) {
        if (menuInput.equals(CHECK_ATTENDANCE.getValue())) {
            checkAttendance();
        }
        if (menuInput.equals(MODIFY_ATTENDANCE.getValue())) {
            modifyAttendance();
        }
        if (menuInput.equals(CHECK_CREW_ATTENDANCE_HISTORY.getValue())) {
            checkCrewAttendanceHistory();
        }
        if (menuInput.equals(CHECK_DANGEROUS_CREW.getValue())) {
            checkDangerousCrew();
        }
        if (quitPattern.matcher(menuInput).matches()){
            return false;
        }
        return true;
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
        System.out.println(outputView.printCheckedAttendance(allCrew.addCrewAttendanceByName(name, LocalDateTime.of(todayDate.getYear(),
                todayDate.getMonthValue(),
                todayDate.getDayOfMonth(),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1])))));
    }

    private void modifyAttendance() {
        String name = userInputView.askNickNameForModifyAttendanceInfo();
        int day = userInputView.askDayForModifyAttendanceInfo();
        String[] time = userInputView.askAttendanceTimeForModifyAttendance();
        LocalDateTime dateTime = LocalDateTime.of(todayDate.getYear(),
                todayDate.getMonthValue(),
                day,
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1]));
        outputView.printModifyAttendance(allCrew, name, dateTime);
    }
}

