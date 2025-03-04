package attendance.view;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import attendance.domain.PenaltyType;
import attendance.domain.RiskCrew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class OutputView {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm",
            Locale.KOREA);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE", Locale.KOREA);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);

    public void writeAttendanceRegister(AttendanceTime registerdAttendanceTime) {
        LocalDate attendanceDates = registerdAttendanceTime.getAttendanceDate();
        LocalTime attendanceTimes = registerdAttendanceTime.getAttendanceTime();
        LocalDateTime attendanceTime = LocalDateTime.of(attendanceDates, attendanceTimes);
        AttendanceStatus attendanceStatus = registerdAttendanceTime.getAttendanceStatus();
        String formattedDate = attendanceTime.format(dateTimeFormatter);

        System.out.println(formattedDate + " (" + attendanceStatus.getName() + ")");
    }

    public void writeErrorMessage(String message) {
        System.out.println(message);
    }

    public void writeAttendanceModify(AttendanceTime beforeTime, AttendanceTime updateTime) {
        AttendanceStatus beforeAttendanceStatus = beforeTime.getAttendanceStatus();
        AttendanceStatus updateAttendanceStatus = updateTime.getAttendanceStatus();
        LocalDate beforeAttendanceDate = beforeTime.getAttendanceDate();
        LocalTime beforeAttendanceTime = beforeTime.getAttendanceTime();
        if (beforeAttendanceTime == null) {
            System.out.print(
                    beforeAttendanceDate.format(dateFormatter) + " --:-- " + "(" + beforeAttendanceStatus.getName()
                            + ")");
        }
        LocalDateTime updateAttendanceTime = LocalDateTime.of(updateTime.getAttendanceDate(),
                updateTime.getAttendanceTime());
        String updateFormat = updateAttendanceTime.format(timeFormatter);
        System.out.println(" -> " + updateFormat + " (" + updateAttendanceStatus.getName() + ") 수정 완료!");
    }

    public void writeAttendanceCheck(Crew crewName, AttendanceRecord attendanceRecord) {
        System.out.println("이번 달 " + crewName.getName() + "의 출석 기록입니다.");
        System.out.println();

        for (AttendanceTime attendanceTime : attendanceRecord.getAttendanceRecord()) {
            AttendanceStatus status = attendanceTime.getAttendanceStatus();
            LocalDate localDate = attendanceTime.getAttendanceDate();
            LocalTime localTime = attendanceTime.getAttendanceTime();
            writeDefaultHyphen(localDate, localTime, status);
        }
        writeAttendanceRecord(attendanceRecord);
    }

    private void writeAttendanceRecord(AttendanceRecord attendanceRecord) {
        System.out.println();
        System.out.println("출석: " + attendanceRecord.checkAttendanceCounts() + "회");
        System.out.println("지각: " + attendanceRecord.checkLateCounts() + "회");
        System.out.println("결석: " + attendanceRecord.checkAbsenceCounts() + "회");
        System.out.println();
        System.out.println(writePenaltyStatus(attendanceRecord.checkPenaltyStatus()) + " 대상자입니다.");
    }

    private String writePenaltyStatus(PenaltyType penaltyType) {
        if (penaltyType.equals(PenaltyType.EXPULSION)) {
            return "제적";
        }
        if (penaltyType.equals(PenaltyType.COUNSELING)) {
            return "면담";
        }
        if (penaltyType.equals(PenaltyType.WARNING)) {
            return "경고";
        }
        return "성실";
    }

    private void writeDefaultHyphen(LocalDate localDate, LocalTime localTime, AttendanceStatus status) {
        if (localTime == null) {
            System.out.println(localDate.format(dateFormatter) + " --:-- " + "(" + status.getName() + ")");
            return;
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        System.out.println(localDateTime.format(dateTimeFormatter) + " (" + status.getName() + ")");
    }

    public void writeRiskCrews(List<RiskCrew> riskCrews) {
        System.out.println("제적 위험자 조회 결과");
        for (RiskCrew riskCrew : riskCrews) {
            String riskCrewName = riskCrew.getName();
            int absenceCounts = riskCrew.getAbsenceCount();
            int lateCounts = riskCrew.getLateCount();
            PenaltyType penaltyType = riskCrew.getPenaltyType();
            System.out.println("- " + riskCrewName + ": " + "결석 " + absenceCounts + "회, 지각 "
                    + lateCounts + "회 (" + writePenaltyStatus(penaltyType) + ")");
        }
    }

}
