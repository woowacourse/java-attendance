package controller;

import domain.AllCrew;
import domain.Crew;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class AttendanceController {
    AllCrew allCrew = new AllCrew();
    LocalDate today = LocalDate.of(2024, 12, 13);

    public void run() {
        readAttendanceFile(allCrew);

        while (true) {
            String str = "오늘은 " + today.format(DateTimeFormatter.ofPattern("MM월 dd일 ")) +
                    today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + "입니다. ";
            str += "기능을 선택해 주세요.";
            System.out.println(str);
            String menu = "1. 출석 확인\n"
                    + "2. 출석 수정\n"
                    + "3. 크루별 출석 기록 확인\n"
                    + "4. 제적 위험자 확인\n"
                    + "Q. 종료";
            System.out.println(menu);
            Scanner sc = new Scanner(System.in);
            final String  menuInput = sc.nextLine();
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
            // TODO:메뉴 입력 예외

        }

    }

    private void checkDangerousCrew() {
        System.out.println("제적 위험자 조회 결과");
        System.out.println(allCrew.printAllCrewWarningInfo(today));
    }

    private void checkCrewAttendanceInfo() {
        System.out.println("닉네임을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        System.out.println("\n이번 달 " + name + "의 출석 기록입니다.\n");

        System.out.println(allCrew.printAttendanceHistory(name, today));
    }

    private void readAttendanceFile(AllCrew allCrew) {
        try {
            FileReader fileReader = new FileReader("src/main/attendance.csv");
            Scanner scanner = new Scanner(fileReader);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String crewName = line.split(",")[0];
                if (!allCrew.containsCrewName(crewName)){
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
        System.out.println("닉네임을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("등교 시간을 입력해 주세요.");
        String[] time = sc.nextLine().split(":");

        System.out.println(allCrew.addCrewAttendanceByName(name, LocalDateTime.of(today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1]))));
    }

    private void modifyAttendance() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        int day = Integer.parseInt(sc.nextLine());
        System.out.println("언제로 변경하겠습니까?");
        String[] times = sc.nextLine().split(":");

        System.out.println("\n"+
                allCrew.modifyCrewAttendanceByName(name, LocalDateTime.of(today.getYear(),
                        today.getMonthValue(),
                        day,
                        Integer.parseInt(times[0]),
                        Integer.parseInt(times[1])))
        );
    }
}

