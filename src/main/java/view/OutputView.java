package view;

import domain.AllCrew;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OutputView {
    private final LocalDate today;

    public OutputView(LocalDate today) {
        this.today = today;
    }

    public void printDangerousCrew(AllCrew allCrew) {
        System.out.println("\n제적 위험자 조회 결과");
        System.out.println(allCrew.printAllCrewWarningInfo(today));
    }

    public void printAttendanceHistory(AllCrew allCrew, String name) {
        System.out.println("\n이번 달 " + name + "의 출석 기록입니다.\n");
        System.out.println(allCrew.printAttendanceHistory(name, today));
    }

    public void printCheckedAttendance(AllCrew allCrew, String name, String time){
        System.out.println("\n" + allCrew.addCrewAttendanceByName(name, LocalDateTime.of(today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]))) + "\n");
    }

    public void printModifyAttendance(AllCrew allCrew, String name, String day, String time){
        System.out.println("\n" +
                allCrew.modifyCrewAttendanceByName(name, LocalDateTime.of(today.getYear(),
                        today.getMonthValue(),
                        Integer.parseInt(day),
                        Integer.parseInt(time.split(":")[0]),
                        Integer.parseInt(time.split(":")[1]))
                ));
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }
}
