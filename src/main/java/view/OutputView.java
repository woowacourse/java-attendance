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

    public void printAttendanceResult(String attendanceResult){
        System.out.println("\n" + attendanceResult + "\n");
    }

    public void printModifyAttendance(String modifyResult){
        System.out.println("\n" + modifyResult + "\n");
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }
}
