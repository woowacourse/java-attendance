package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceTime;
import attendance.domain.CrewAttendanceInformation;
import java.util.List;

public class OutputView {

    public void printAcademicStatusResult(CrewAttendanceInformation crewAttendanceInformation) {

        System.out.println("출석: " + crewAttendanceInformation.attend() + "회");
        System.out.println("지각: " + crewAttendanceInformation.late() + "회");
        System.out.println("결석: " + crewAttendanceInformation.absent() + "회");

        if (crewAttendanceInformation.academicStatus().equals("X")) {
            System.out.println("대상자가 아닙니다.");
            return;
        }
        System.out.println(crewAttendanceInformation.academicStatus() + " 대상자입니다.");
        printNewLine();
    }

    public void printModifyAttendanceResult(AttendanceTime originAttendanceTime, String originAttendanceStatus,
                                            AttendanceTime modifyAttendanceTime, String modifyAttendanceStatus) {

        System.out.printf("%02d월 %02d일 %s %s:%s (%s)", originAttendanceTime.getMonth(), originAttendanceTime.getDay(),
                originAttendanceTime.getDayOfWeek(), originAttendanceTime.hour(), originAttendanceTime.minute(),
                originAttendanceStatus);

        System.out.print(" -> ");

        System.out.printf("%s:%s (%s)", modifyAttendanceTime.hour(), modifyAttendanceTime.minute(),
                modifyAttendanceStatus);

        System.out.println(" 수정 완료!");
        printNewLine();
    }

    public void printNameAndAttendances(String name, List<Attendance> attendances) {

        System.out.println("이번 달 " + name + "의 출석 기록입니다.");
        for (Attendance attendance : attendances) {
            String attendanceStatus = attendance.getAttendanceStatus();
            printAttendance(attendance.getAttendanceTime(), attendanceStatus);
        }
        printNewLine();
    }

    public void printAttendance(AttendanceTime attendanceTime, String attendanceStatus) {

        System.out.printf("%02d월 %02d일 %s %s:%s (%s)%n", attendanceTime.getMonth(), attendanceTime.getDay(),
                attendanceTime.getDayOfWeek(), attendanceTime.hour(), attendanceTime.minute(),
                attendanceStatus);
    }

    public void printCrewsAtRiskOfExpulsionStartMessage() {

        System.out.println("제적 위험자 조회 결과");
    }

    public void printCrewsAtRiskOfExpulsion(List<CrewAttendanceInformation> crewAttendanceHistories) {

        for (CrewAttendanceInformation crewAttendanceInformation : crewAttendanceHistories) {
            System.out.print("- " + crewAttendanceInformation.crewName() + ": ");
            System.out.print("결석: " + crewAttendanceInformation.absent() + "회, ");
            System.out.print("지각: " + crewAttendanceInformation.late() + "회 ");
            System.out.println("(" + crewAttendanceInformation.academicStatus() + ")");
        }
    }

    public void printErrorMessage(String message) {

        System.out.println(message);
    }

    public void printNewLine() {

        System.out.println();
    }
}
