package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.Time;
import attendance.dto.CrewNameAndAcademicStatusDTO;
import java.util.List;

public class OutputView {

    public void printAcademicStatusResult(CrewNameAndAcademicStatusDTO attendanceCountAndAcademicStatusDTO) {

        System.out.println("출석: " + attendanceCountAndAcademicStatusDTO.attend() + "회");
        System.out.println("지각: " + attendanceCountAndAcademicStatusDTO.late() + "회");
        System.out.println("결석: " + attendanceCountAndAcademicStatusDTO.absent() + "회");

        if (attendanceCountAndAcademicStatusDTO.academicStatus().equals("X")) {
            System.out.println("대상자가 아닙니다.");
            return;
        }
        System.out.println(attendanceCountAndAcademicStatusDTO.academicStatus() + " 대상자입니다.");
    }

    public void printModifyAttendanceResult(Time originTime, String originAttendanceStatus,
                                            Time modifyTime, String modifyAttendanceStatus) {
        System.out.printf("%02d월 %02d일 %s %s:%s (%s)", originTime.getMonth(), originTime.getDay(),
                originTime.getDayOfWeek(), originTime.getHour(), originTime.getMinute(), originAttendanceStatus);

        System.out.print(" -> ");

        System.out.printf("%s:%s (%s)", modifyTime.getHour(), modifyTime.getMinute(),
                modifyAttendanceStatus);

        System.out.println(" 수정 완료!");

    }

    public void printNameAndAttendances(String name, List<Attendance> attendances) {

        System.out.println("이번 달 " + name + "의 출석 기록입니다.");
        for (Attendance attendance : attendances) {
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

    public void printCrewsAtRiskOfExpulsionStartMessage() {
        System.out.println("제적 위험자 조회 결과");
    }

    public void printCrewsAtRiskOfExpulsion(List<CrewNameAndAcademicStatusDTO> crewNameAndAcademicStatusDTOList) {

        for (CrewNameAndAcademicStatusDTO crewNameAndAcademicStatusDTO : crewNameAndAcademicStatusDTOList) {
            System.out.print("- " + crewNameAndAcademicStatusDTO.crewName() + ": ");
            System.out.print("결석: " + crewNameAndAcademicStatusDTO.absent() + "회, ");
            System.out.print("지각: " + crewNameAndAcademicStatusDTO.late() + "회 ");
            System.out.println("(" + crewNameAndAcademicStatusDTO.academicStatus() + ")");

        }
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
