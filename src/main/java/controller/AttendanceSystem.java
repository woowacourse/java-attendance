package controller;

import static java.lang.Integer.parseInt;
import static view.OutputView.getFormattedDayInfo;
import static view.OutputView.printCheckedAttendance;
import static view.OutputView.printModifyResult;
import static view.UserInputView.*;

import view.MenuOption;
import domain.AllCrew;
import domain.Crew;
import view.OutputView;
import view.UserInputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceSystem {
    private final LocalDate today;
    private final AllCrew allCrew;

    public AttendanceSystem() {
        today = LocalDate.now();
        allCrew = new AllCrew();
        allCrew.updateFile("src/main/resources/attendances.csv");
        allCrew.fillAllCrewsEmptyDateWithAbsent(today.minusDays(1));
    }

    public void run() {
        while (true) {
            System.out.println("오늘은 " + getFormattedDayInfo(today) + "입니다. 기능을 선택해 주세요.");
            MenuOption option = UserInputView.askMenuOption();
            if (option == MenuOption.CHECK_ATTENDANCE) {
                checkAttendance();
            }
            if (option == MenuOption.MODIFY_ATTENDANCE) {
                modifyAttendance();
            }
            if (option == MenuOption.CHECK_CREW_ATTENDANCE_HISTORY) {
                checkCrewattendances();
            }
            if (option == MenuOption.CHECK_PENALTY_RECEIVED_CREW) {
                checkPenaltyReceivedCrew();
            }
            if (option == MenuOption.QUIT) {
                return;
            }
        }
    }

    private void checkAttendance() {
        String crewName = askCrewName();
        Crew crew = allCrew.findCrewByName(crewName);
        List<String> hourAndMinute = List.of(askAttendanceTime().split(":"));
        LocalTime time = LocalTime.of(parseInt(hourAndMinute.getFirst()), parseInt(hourAndMinute.getLast()));
        printCheckedAttendance(crew.addAttendanceWithDateTime(LocalDateTime.of(today, time)));
    }

    private void modifyAttendance() {
        Crew crew = allCrew.findCrewByName(askCrewName());
        int date = parseInt(askAttendedDate());
        List<String> hourAndMinute = List.of(askTimeForModify().split(":"));
        LocalTime timeTo = LocalTime.of(parseInt(hourAndMinute.getFirst()), parseInt(hourAndMinute.getLast()));
        printModifyResult(crew.modifyAttendedTime(date, timeTo));
    }

    private void checkCrewattendances() {
        String crewName = askCrewName();
        Crew crew = allCrew.findCrewByName(crewName);
        OutputView.printCrewAttendances(crew);
    }

    private void checkPenaltyReceivedCrew() {
        List<Crew> penaltyReceivedCrew = allCrew.getPenaltyReceivedCrew();
        allCrew.sortPenaltyReceivedCrew(penaltyReceivedCrew);
        OutputView.printPenaltyReceivedCrew(penaltyReceivedCrew);
    }

}
