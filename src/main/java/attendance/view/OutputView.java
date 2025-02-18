package attendance.view;

import attendance.domain.Attendance;
import attendance.utils.AttendanceChecker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputView {

    public void printExpelledResult(String status) {

        if (status.equals("X")) {
            System.out.println("대상자가 아닙니다.");
            return;
        }
        System.out.println(status + " 대상자입니다.");
    }

    public void printAttendanceStatistics(int attendance, int late, int absent) {
        System.out.println("출석: " + attendance + "회");
        System.out.println("지각: " + late + "회");
        System.out.println("결석: " + absent + "회");
    }

    public void printModifyAttendanceResult(LocalDateTime originTime, LocalDateTime modifyTime) {
        printAttendance(originTime, AttendanceChecker.check(originTime));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(
                " ->  " + modifyTime.format(formatter) + "(" + AttendanceChecker.check(modifyTime) + ") 수정 완료!");
    }

    public void printNameAndAttendances(String name, List<Attendance> attendances) {

        System.out.println("이번 달 " + name + "의 출석 기록입니다.");
        for (Attendance attendance : attendances) {
            String attendanceStatus = AttendanceChecker.check(attendance.getAttendanceTime());
            printAttendance(attendance.getAttendanceTime(), attendanceStatus);
        }
    }

    public void printAttendance(LocalDateTime attendanceTime, String attendanceStatus) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm");
        System.out.print(attendanceTime.format(formatter) + " (" + attendanceStatus + ")");
    }

}
