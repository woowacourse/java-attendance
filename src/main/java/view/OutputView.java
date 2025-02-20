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
        System.out.println(allCrew.printAttendanceHistory(name, today));
    }

    public void printCheckedAttendance(AllCrew allCrew, String name, String[] time){
        System.out.println(allCrew.addCrewAttendanceByName(name, LocalDateTime.of(today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1]))));
    }

    public void printModifyAttendance(AllCrew allCrew, String name, int day, String[] time){
        System.out.println("\n" +
                allCrew.modifyCrewAttendanceByName(name, LocalDateTime.of(today.getYear(),
                        today.getMonthValue(),
                        day,
                        Integer.parseInt(time[0]),
                        Integer.parseInt(time[1])))
        );
    }

}
