package attendance.view;

import attendance.domain.Crew;
import attendance.domain.DateInfo;
import attendance.domain.AttendanceRegistry;
import attendance.domain.constant.CrewStatus;
import attendance.domain.constant.Weekday;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OutputView {
    public void writeAttendanceCheck(DateInfo dateInfo) {
        String month = addZero(dateInfo.getLocalDateTime().getMonthValue());
        String day = addZero(dateInfo.getLocalDateTime().getDayOfMonth());
        String dayOfWeek = Weekday.from(dateInfo.getLocalDateTime().getDayOfWeek()).getDayOfWeek();
        String time = convertZeroToHyphen(dateInfo.getLocalDateTime().getHour(), dateInfo.getLocalDateTime().getMinute());
        String status = dateInfo.getAttendanceStatus();
        System.out.println(String.format("%s월 %s일 %s %s (%s)", month, day, dayOfWeek, time, status));
    }

    public void writeAttendanceModifyCheck(int beforeHour, int beforeMinute, String beforeStatus, DateInfo modifiedInfo) {
        String month = addZero(modifiedInfo.getLocalDateTime().getMonthValue());
        String day = addZero(modifiedInfo.getLocalDateTime().getDayOfMonth());
        String dayOfWeek = Weekday.from(modifiedInfo.getLocalDateTime().getDayOfWeek()).getDayOfWeek();
        String hour = addZero(modifiedInfo.getLocalDateTime().getHour());
        String minute = addZero(modifiedInfo.getLocalDateTime().getMinute());
        String status = modifiedInfo.getAttendanceStatus();
        System.out.println(String.format("%s월 %s일 %s %s (%s) -> %s:%s (%s) 수정 완료!", month, day, dayOfWeek, convertZeroToHyphen(beforeHour,beforeMinute), beforeStatus, hour, minute, status));
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
        List<Map.Entry<Crew, List<Integer>>> crewList = new ArrayList<>(allExpertRiskCrews.entrySet());
        crewList.sort((e1, e2) -> Integer.compare(
                calculateTotalAbsence(e2.getValue()),
                calculateTotalAbsence(e1.getValue())
        ));
        Set<Crew> excludedCrews = orderByAbsence(crewList);
        orderByNickname(crewList, excludedCrews);
    }

    private int calculateTotalAbsence(List<Integer> absenceCounts) {
        int absence = absenceCounts.get(0);
        int late = absenceCounts.get(1);
        return absence + (late / 3);
    }

    private Set<Crew> orderByAbsence(List<Entry<Crew, List<Integer>>> crewList) {
        Set<Crew> excludedCrews = new HashSet<>();
        int maxAbsence = calculateTotalAbsence(crewList.get(0).getValue());
        Iterator<Entry<Crew, List<Integer>>> iterator = crewList.iterator();
        while (iterator.hasNext()) {
            Entry<Crew, List<Integer>> entry = iterator.next();
            checkMaxAbsence(entry, maxAbsence, excludedCrews, iterator);
        }
        return excludedCrews;
    }

    private void orderByNickname(List<Entry<Crew, List<Integer>>> crewList, Set<Crew> excludedCrews) {
        List<Entry<Crew, List<Integer>>> remainingCrewList = crewList.stream()
                .filter(entry -> !excludedCrews.contains(entry.getKey()))
                .sorted(Comparator.comparing(entry -> entry.getKey().getCrewName()))
                .toList();

        for (Entry<Crew, List<Integer>> entry : remainingCrewList) {
            writeEntry(entry);
        }
    }

    private void checkMaxAbsence(Entry<Crew, List<Integer>> entry, int maxAbsence, Set<Crew> excludedCrews,
                           Iterator<Entry<Crew, List<Integer>>> iterator) {
        if (entry.getValue().get(0) == maxAbsence) {
            writeEntry(entry);
            excludedCrews.add(entry.getKey());
            iterator.remove();
        }
    }

    private void writeEntry(Map.Entry<Crew, List<Integer>> entry) {
        Crew crew = entry.getKey();
        String crewName = crew.getCrewName();
        int absenceCounts = entry.getValue().get(0);
        int lateCounts = entry.getValue().get(1);

        CrewStatus crewStatus = CrewStatus.from(lateCounts, absenceCounts);
        writeOrderedCrews(crewStatus, absenceCounts, crewName, lateCounts);
    }

    private void writeOrderedCrews(CrewStatus crewStatus, int absenceCounts, String crewName, int lateCounts) {
        if (crewStatus.equals(CrewStatus.DISMISS)) {
            writeAbsenceOver(absenceCounts, crewName, lateCounts, crewStatus.getName());
            return;
        }
        if (crewStatus.equals(CrewStatus.WARNING)) {
            writeAbsenceOver(absenceCounts, crewName, lateCounts, crewStatus.getName());
            return;
        }
        writeAbsenceOver(absenceCounts, crewName, lateCounts, crewStatus.getName());
    }

    private void writeAbsenceOver(int absenceCounts, String crewName, int lateCounts, String crewStatus) {
        if (absenceCounts >= 2) {
            System.out.println(String.format("- %s: 결석 %d회, 지각 %d회 (%s)", crewName, absenceCounts, lateCounts,
                    crewStatus));
        }
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