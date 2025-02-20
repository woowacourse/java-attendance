package controller;

import domain.AllCrew;
import domain.Crew;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    AllCrew allCrew = new AllCrew();
    LocalDate today = LocalDate.of(2024, 12, 13);

    public void run() {
        readAttendanceFile(allCrew);
        allCrew.updateAbsentHistory(today.minusDays(1));

        while (true) {
            String menuInput = InputView.showMenu();
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
        String name = InputView.askNickNameForCheckAttendanceInfo();
        OutputView.printAttendanceHistory(allCrew, name);
    }

    private void readAttendanceFile(AllCrew allCrew) {
        try {
            FileReader fileReader = new FileReader("src/main/attendance.csv");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String crewName = line.split(",")[0];
                if (!allCrew.containsCrewName(crewName)) {
                    allCrew.addCrew(new Crew(crewName));
                }
                initializeCrewInfo(allCrew, line, crewName);
            }
        } catch (FileNotFoundException e) {
            System.out.println("없는 파일입니다.");
        }
    }

    private static void initializeCrewInfo(AllCrew allCrew, String line, String crewName) {
        String[] attendanceDateTime = line.split(",")[1].split(" ");
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(attendanceDateTime[0]),
                Integer.parseInt(attendanceDateTime[1]),
                Integer.parseInt(attendanceDateTime[2]),
                Integer.parseInt(attendanceDateTime[3]),
                Integer.parseInt(attendanceDateTime[4]));
        allCrew.addCrewAttendanceByName(crewName, localDateTime);
    }

    private void checkAttendance() {
        String name = InputView.askNickNameForCheckAttendance();
        String[] time = InputView.askAttendanceTimeForCheckAttendance();
        OutputView.printCheckedAttendance(allCrew, name, time);
    }

    private void modifyAttendance() {
        String name = InputView.askNickNameForModifyAttendanceInfo();
        int day = InputView.askDayForModifyAttendanceInfo();
        String[] time = InputView.askAttendanceTimeForModifyAttendance();
        OutputView.printModifyAttendance(allCrew, name, day, time);
    }
}

