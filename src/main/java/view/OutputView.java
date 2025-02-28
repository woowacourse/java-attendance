package view;

import common.Campus;
import common.DateTimeFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.attendance.Attendance;
import model.attendance.AttendanceStatistic;
import model.attendance.AttendanceStatus;
import model.attendance.Crew;
import model.attendance.PenaltyStatus;

public class OutputView {
    public void printAttendanceRegisterResult(Attendance newAttendance) {
        System.out.println();
        System.out.printf("%s %s (%s)%n",
                newAttendance.getDate().format(DateTimeFormat.MONTH_DATE_DAY_FORMATTER),
                newAttendance.getTime().format(DateTimeFormat.HOUR_MINUTE_FORMATTER),
                newAttendance.findStatus().getMeaning()
        );
    }

    public void printExceptionMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAttendanceModifyResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.println();
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                oldAttendance.getDate().format(DateTimeFormat.MONTH_DATE_DAY_FORMATTER),
                getAttendanceTimeExpression(oldAttendance),
                oldAttendance.findStatus().getMeaning(),
                getAttendanceTimeExpression(newAttendance),
                newAttendance.findStatus().getMeaning()
        );
    }

    public void printAttendanceHistories(List<Attendance> attendanceHistories, String crewName) {
        System.out.println();
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", crewName);

        for (Attendance attendance : attendanceHistories) {
            System.out.printf("%s %s (%s)%n",
                    attendance.getDate().format(DateTimeFormat.MONTH_DATE_DAY_FORMATTER),
                    getAttendanceTimeExpression(attendance),
                    attendance.findStatus().getMeaning()
            );
        }

    }

    public void printAttendanceStatusHistory(Map<AttendanceStatus, Integer> attendanceStatusHistory) {
        System.out.println();
        for (AttendanceStatus status : AttendanceStatus.findAllInAscendingOrder()) {
            System.out.printf("%s: %d회%n", status.getMeaning(), attendanceStatusHistory.get(status));
        }
    }

    public void printPenaltyStatus(PenaltyStatus penaltyStatus) {
        if (penaltyStatus == PenaltyStatus.NONE) {
            return;
        }
        System.out.println();
        System.out.printf("%s 대상자입니다.%n", penaltyStatus.getMeaning());
    }

    private String getAttendanceTimeExpression(Attendance attendance) {
        if (attendance.getTime().equals(Campus.NONE_ATTENDANCE_TIME)) {
            return "--:--";
        }
        return attendance.getTime().format(DateTimeFormat.HOUR_MINUTE_FORMATTER);
    }

    public void printPenaltyResult(Map<Crew, AttendanceStatistic> penaltyTargets) {
        System.out.println();
        System.out.println("제적 위험자 조회 결과");
        Map<Crew, AttendanceStatistic> sortedPenaltyTargets = sortStatistics(penaltyTargets);
        for (Crew crew : sortedPenaltyTargets.keySet()) {
            AttendanceStatistic statistic = penaltyTargets.get(crew);
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    crew.getName(),
                    statistic.getAttendanceCount().get(AttendanceStatus.ABSENCE),
                    statistic.getAttendanceCount().get(AttendanceStatus.LATE),
                    statistic.getPenaltyStatus().getMeaning()
            );
        }
    }

    private Map<Crew, AttendanceStatistic> sortStatistics(Map<Crew, AttendanceStatistic> origin) {
        Map<Crew, AttendanceStatistic> sorted = new HashMap<>();
        origin.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<Crew, AttendanceStatistic> entrySet) -> {
                            Map<AttendanceStatus, Integer> attendanceCount = entrySet.getValue().getAttendanceCount();
                            return PenaltyStatus.calculateFinalAbsenceCount(
                                    attendanceCount.get(AttendanceStatus.LATE),
                                    attendanceCount.get(AttendanceStatus.ABSENCE)
                            );
                        }).reversed()
                        .thenComparing((Map.Entry<Crew, AttendanceStatistic> entrySet) -> entrySet.getKey().getName())
                )
                        .forEach((Map.Entry<Crew, AttendanceStatistic> entrySet) -> {
                            sorted.put(entrySet.getKey(), entrySet.getValue());
                        });
        return sorted;
    }
}
