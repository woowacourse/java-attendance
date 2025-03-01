package controller;

import static java.lang.Integer.parseInt;
import static view.OutputView.getFormattedDayInfo;
import static view.OutputView.printCheckedAttendance;
import static view.OutputView.printMenu;
import static view.OutputView.printModifyResult;
import static view.UserInputView.*;

import domain.AllCrew;
import domain.Crew;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class AttendanceSystem {
    private final LocalDate today;
    private final AllCrew allCrew;

    public AttendanceSystem() throws FileNotFoundException {
        today = LocalDate.now();
        allCrew = new AllCrew(new File("src/main/resources/attendances.csv"));
        allCrew.fillAllCrewsEmptyDateWithAbsent(today);
    }

    public void run() throws FileNotFoundException {
        while (true) {
            System.out.println("오늘은 " + getFormattedDayInfo(today) + "입니다. 기능을 선택해 주세요.");
            printMenu();
            String option = new Scanner(System.in).nextLine();
            if (option.equals("1")) {
                checkAttendance();
            }
            if (option.equals("2")) {
                modifyAttendance();
            }
            if (option.equals("3")) {
                checkCrewAttendanceHistory();
            }
            if (option.equals("4")) {
//                checkPenaltyReceivedCrew();
            }
            if (option.equals("q")) {
                return;
            }
        }
    }

    private void checkCrewAttendanceHistory() {
        String crewName = askCrewName();
        Crew crew = allCrew.findCrewByName(crewName);
        OutputView.printCrewAttendanceHistory(crew);
    }
    private void modifyAttendance() {
        String crewName = askCrewName();
        Crew crew = allCrew.findCrewByName(crewName);
        int date = parseInt(askAttendedDate());
        List<String> hourAndMinute = List.of(askTimeForModify().split(":"));
        LocalTime timeTo = LocalTime.of(parseInt(hourAndMinute.getFirst()), parseInt(hourAndMinute.getLast()));
        printModifyResult(crew.modifyAttendedTime(date, timeTo));
    }

    private void checkAttendance() {
        String crewName = askCrewName();
        Crew crew = allCrew.findCrewByName(crewName);
        List<String> hourAndMinute = List.of(askAttendanceTime().split(":"));
        LocalTime time = LocalTime.of(parseInt(hourAndMinute.getFirst()), parseInt(hourAndMinute.getLast()));
        printCheckedAttendance(crew.addAttendanceWithDateTime(LocalDateTime.of(today, time)));
    }

}
