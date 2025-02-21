package view;

import domain.AttendanceBook;
import java.time.LocalDate;

public class OutputView {
    private final LocalDate today;

    public OutputView(LocalDate today) {
        this.today = today;
    }

    public void printPenaltyCrew(AttendanceBook attendanceBook) {
        System.out.println("\n제적 위험자 조회 결과");
        System.out.println(attendanceBook.printAllCrewWarningInfo(today));
    }

    public void printAttendanceHistory(AttendanceBook attendanceBook, String name) {
        System.out.println("\n이번 달 " + name + "의 출석 기록입니다.\n");
        System.out.println(attendanceBook.printAttendanceHistory(name, today));
    }

    public void printAttendanceResult(String attendanceResult){
        System.out.println("\n" + attendanceResult + "\n");
    }

    public void printModifiedAttendance(String modifyResult){
        System.out.println("\n" + modifyResult + "\n");
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }
}
