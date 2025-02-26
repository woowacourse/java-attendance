package controller;

import domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import view.FileInputView;
import view.OutputView;
import view.UserInputView;

import static domain.MenuOption.*;

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
        this.outputView = new OutputView();
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
        MenuOption menuOption = MenuOption.getMenuOption(menuInput);
        if (menuOption == CHECK_ATTENDANCE) {
            checkAttendance();
        }
        if (menuOption == MODIFY_ATTENDANCE) {
            modifyAttendance();
        }
        if (menuOption == CHECK_CREW_ATTENDANCE_HISTORY) {
            checkCrewAttendanceHistory();
        }
        if (menuOption == CHECK_DANGEROUS_CREW) {
            checkDangerousCrew();
        }
        if (quitPattern.matcher(menuInput).matches()){
            return false;
        }
        return true;
    }

    private void checkDangerousCrew() {
        allCrew.sortAllCrewOrderByWarningInfo();
        List<Crew> allAbsentPenaltyReceivedCrew = allCrew.getAllAbsentPenaltyReceivedCrew();
        outputView.printAllDangerousCrew(allAbsentPenaltyReceivedCrew);
    }

    private void checkCrewAttendanceHistory() {
        String name = userInputView.askNickNameForCheckAttendanceInfo();
        Crew crew = allCrew.findCrewByName(name);
        crew.sortAttendanceInfo();
        outputView.printAttendanceHistory(crew);
    }

    private void checkAttendance() {
        String name = userInputView.askNickNameForCheckAttendance();
        ArrayList<String> time = userInputView.askAttendanceTimeForCheckAttendance();
        outputView.printCheckedAttendance(allCrew.addCrewAttendanceByName(name, LocalDateTime.of(todayDate.getYear(),
                todayDate.getMonthValue(),
                todayDate.getDayOfMonth(),
                Integer.parseInt(time.get(0)),
                Integer.parseInt(time.get(1)))));
    }

    private void modifyAttendance() {
        String name = userInputView.askNickNameForModifyAttendanceInfo();
        int day = userInputView.askDayForModifyAttendanceInfo();
        ArrayList<String> time = userInputView.askAttendanceTimeForModifyAttendance();
        LocalDateTime dateTime = LocalDateTime.of(todayDate.getYear(),
                todayDate.getMonthValue(),
                day,
                Integer.parseInt(time.get(0)),
                Integer.parseInt(time.get(1)));
        AttendanceUpdateResult attendanceUpdateResult = allCrew.modifyCrewAttendanceByName(name, dateTime);
        outputView.printModifyAttendance(attendanceUpdateResult.getOldAttendance(), attendanceUpdateResult.getNewAttendance());
    }
}

