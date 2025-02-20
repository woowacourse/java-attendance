package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.Time;
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

    public void printModifyAttendanceResult(Time originTime, String originAttendanceStatus,
                                            Time modifyTime, String modifyAttendanceStatus) {
        System.out.printf("%02d월 %02d일 %s %s:%s (%s)", originTime.getMonth(), originTime.getDay(),
                originTime.getDayOfWeek(), originTime.getHour(), originTime.getMinute(), originAttendanceStatus);

        System.out.print(" -> ");

        System.out.printf("%s:%s (%s)", modifyTime.getHour(), modifyTime.getMinute(),
                modifyAttendanceStatus);

        System.out.print(" 수정 완료!");

    }

    public void printNameAndAttendances(String name, List<Attendance> attendances) {

        System.out.println("이번 달 " + name + "의 출석 기록입니다.");
        for (Attendance attendance : attendances) {
            if (attendance.getAttendanceTime() == null) {
                printAbsentAttendance();
                continue;
            }
            String attendanceStatus = attendance.getAttendanceStatus();
            printAttendance(attendance.getAttendanceTime(), attendanceStatus);
        }
    }

    public void printAttendance(Time attendanceTime, String attendanceStatus) {

        System.out.println(
                String.format("%02d월 %02d일 %s %s:%s (%s)", attendanceTime.getMonth(), attendanceTime.getDay(),
                        attendanceTime.getDayOfWeek(), attendanceTime.getHour(), attendanceTime.getMinute(),
                        attendanceStatus));
    }

    private void printAbsentAttendance() {

        System.out.println("결석");
    }


}
