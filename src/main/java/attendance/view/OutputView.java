package attendance.view;

import attendance.domain.AbsenceInfo;
import attendance.domain.Crew;
import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceRegistry;
import attendance.domain.CrewRisk;
import attendance.domain.constant.CrewStatus;
import attendance.domain.constant.Weekday;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OutputView {
    public void writeAttendanceCheck(AttendanceChecker attendanceChecker) {
        String month = addZero(attendanceChecker.getLocalDateTime().getMonthValue());
        String day = addZero(attendanceChecker.getLocalDateTime().getDayOfMonth());
        String dayOfWeek = Weekday.from(attendanceChecker.getLocalDateTime().getDayOfWeek()).getDayOfWeek();
        String time = convertZeroToHyphen(
                attendanceChecker.getLocalDateTime().getHour(), attendanceChecker.getLocalDateTime().getMinute());
        String status = attendanceChecker.getAttendanceStatus();
        System.out.println(String.format("%s월 %s일 %s %s (%s)", month, day, dayOfWeek, time, status));
    }

    public void writeAttendanceModifyCheck(LocalTime beforeTime, String beforeStatus, AttendanceChecker modifiedInfo) {
        String month = addZero(modifiedInfo.getLocalDateTime().getMonthValue());
        String day = addZero(modifiedInfo.getLocalDateTime().getDayOfMonth());
        String dayOfWeek = Weekday.from(modifiedInfo.getLocalDateTime().getDayOfWeek()).getDayOfWeek();
        String hour = addZero(modifiedInfo.getLocalDateTime().getHour());
        String minute = addZero(modifiedInfo.getLocalDateTime().getMinute());
        String status = modifiedInfo.getAttendanceStatus();
        System.out.println(String.format("%s월 %s일 %s %s (%s) -> %s:%s (%s) 수정 완료!", month, day, dayOfWeek, convertZeroToHyphen(
                beforeTime.getHour(), beforeTime.getMinute()), beforeStatus, hour, minute, status));
    }

    public void writeAttendanceHistory(Crew crew, AttendanceRegistry attendanceRegistry) {
        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", crew.getCrewName()));
        for (AttendanceChecker attendanceChecker : attendanceRegistry.getDateInfos()) {
            writeAttendanceCheck(attendanceChecker);
        }
        List<Integer> attendanceTraces = attendanceRegistry.getAttendanceTraces();
        System.out.println();
        System.out.println(String.format("출석: %d회", attendanceTraces.getLast()));
        System.out.println(String.format("지각: %d회", attendanceTraces.get(1)));
        System.out.println(String.format("결석: %d회", attendanceTraces.getFirst()));
        writeWarningMessage(attendanceRegistry);
    }

    private void writeWarningMessage(AttendanceRegistry attendanceRegistry) {
        CrewStatus crewStatus = attendanceRegistry.getCrewStatus();
        if (crewStatus.equals(CrewStatus.DISMISS)) {
            System.out.println("제적 대상자입니다.");
            return;
        }
        if (crewStatus.equals(CrewStatus.COUNSELING)) {
            System.out.println("면담 대상자입니다.");
            return;
        }
        if (crewStatus.equals(CrewStatus.WARNING)) {
            System.out.println("경고 대상자입니다.");
        }
    }

    public void writeDismissCrewCheck(List<CrewRisk> allExpertRiskCrews) {
        System.out.println("제적 위험자 조회 결과");
        for (CrewRisk crewRisk : allExpertRiskCrews) {
            AbsenceInfo absenceInfo = crewRisk.getAbsenceInfo();
            int absenceCounts = absenceInfo.getAbsenceCount();
            int lateCounts = absenceInfo.getLateCount();
            String crewName = crewRisk.getCrew().getCrewName();
            String crewStatus = CrewStatus.from(lateCounts, absenceCounts).getName();
            writeAbsenceOver(absenceCounts, crewName, lateCounts, crewStatus);
        }
    }

    private void writeAbsenceOver(int absenceCounts, String crewName, int lateCounts, String crewStatus) {
        System.out.println(String.format("- %s: 결석 %d회, 지각 %d회 (%s)", crewName, absenceCounts, lateCounts,
                crewStatus));
    }

    private String convertZeroToHyphen(int hour, int minute) {
        if (hour == 0 && minute == 0) {
            return "--:--";
        }
        return addZero(hour)+":"+addZero(minute);
    }

    private String addZero(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public void errorMessagePrint(String message) {
        System.out.println(message);
    }

}