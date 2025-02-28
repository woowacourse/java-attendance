package controller;

import static view.OutputView.getFormattedDayInfo;
import static view.OutputView.printMenu;

import domain.AllCrew;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import view.UserInputView;

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
//                modifyAttendance();
            }
            if (option.equals("3")) {
//                checkCrewAttendanceHistory();
            }
            if (option.equals("4")) {
//                checkPenaltyReceivedCrew();
            }
            if (option.equals("q")) {
                return;
            }
        }



    }

    private void checkAttendance() {
        String crewName = UserInputView.askCrewName();
        if (allCrew.containsCrewName(crewName)) {
            List<String> hourAndMinute = List.of(UserInputView.askAttendanceTime().split(":"));
            LocalTime time = LocalTime.of(Integer.parseInt(hourAndMinute.getFirst()), Integer.parseInt(hourAndMinute.getLast()));
            allCrew.findCrewByName(crewName).addAttendanceWithDateTime(LocalDateTime.of(today, time));
            return;
        }
        throw new NoSuchElementException("[ERROR] 등록되지 않은 닉네임입니다.");
    }

}
