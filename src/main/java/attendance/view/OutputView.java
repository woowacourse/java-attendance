package attendance.view;

import attendance.domain.Crew;
import attendance.domain.DateInfo;
import attendance.domain.AttendanceRegistry;
import attendance.domain.constant.CrewStatus;
import java.util.List;
import java.util.Map;

public class OutputView {
    public void writeAttendanceCheck(DateInfo dateInfo) {
        String month = dateInfo.getMonth();
        String day = dateInfo.getDay();
        String dayOfWeek = dateInfo.getDayOfWeek();
        String hour = dateInfo.getTime().getHour();
        String minute = dateInfo.getTime().getMinute();
        String status = dateInfo.getAttendanceStatus();
        System.out.println(String.format("%s월 %s일 %s %s:%s (%s)", month, day, dayOfWeek, hour, minute, status));
    }

    public void writeAttendanceModifyCheck(String beforeHour, String beforeMinute, String beforeStatus, DateInfo modifiedInfo) {
        String month = modifiedInfo.getMonth();
        String day = modifiedInfo.getDay();
        String dayOfWeek = modifiedInfo.getDayOfWeek();
        String hour = modifiedInfo.getTime().getHour();
        String minute = modifiedInfo.getTime().getMinute();
        String status = modifiedInfo.getAttendanceStatus();
        System.out.println(String.format("%s월 %s일 %s %s:%s (%s) -> %s:%s (%s) 수정 완료!", month, day, dayOfWeek, beforeHour,beforeMinute, beforeStatus, hour, minute, status));
    }

    public void writeAttendanceHistory(Crew crew, AttendanceRegistry attendanceRegistry) {
        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", crew.getCrewName()));
        for (DateInfo dateInfo : attendanceRegistry.getDateInfos()) {
            writeAttendanceCheck(dateInfo);
        }
        System.out.println();
        System.out.println(String.format("출석: %d회", attendanceRegistry.getAttendance()));
        System.out.println(String.format("지각: %d회", attendanceRegistry.getLate()));
        System.out.println(String.format("결석: %d회", attendanceRegistry.getAbsence()));
        writeWarningMessage(attendanceRegistry);
    }

    private void writeWarningMessage(AttendanceRegistry attendanceRegistry) {
        int absence = attendanceRegistry.getAbsence() + attendanceRegistry.getLate()/3;
        if (absence > 5) {
            System.out.println("제적 대상자입니다.");
            return;
        }
        if (absence >= 3) {
            System.out.println("면담 대상자입니다.");
            return;
        }
        if (absence == 2) {
            System.out.println("경고 대상자입니다.");
        }
    }

    public void writeDismissCrewCheck(Map<Crew, List<Integer>> allExpertRiskCrews) {
        System.out.println("제적 위험자 조회 결과");
        for (Crew crew : allExpertRiskCrews.keySet()) {
            String crewName = crew.getCrewName();
            int absenceCounts = allExpertRiskCrews.get(crew).get(0);
            int lateCounts = allExpertRiskCrews.get(crew).get(1);
            String crewStatus = CrewStatus.from(lateCounts, absenceCounts).getName();
            writeAbsenceOver(absenceCounts, crewName, lateCounts, crewStatus);
        }
    }

    private static void writeAbsenceOver(int absenceCounts, String crewName, int lateCounts, String crewStatus) {
        if (absenceCounts >= 2) {
            System.out.println(String.format("- %s: 결석 %d회, 지각 %d회 (%s)", crewName, absenceCounts, lateCounts,
                    crewStatus));
        }
    }

    public void errorMessagePrint(String message) {
        System.out.println(message);
    }

}