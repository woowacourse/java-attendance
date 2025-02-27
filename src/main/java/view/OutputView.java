package view;

import common.Common;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import model.Attendance;
import model.AttendanceStatistic;
import model.AttendanceStatus;
import model.Crew;
import model.DateGenerator;
import model.PenaltyStatus;

public class OutputView {
    public void printAttendanceRegisterResult(Attendance newAttendance) {
        System.out.println();
        System.out.printf("%s %s (%s)%n",
                newAttendance.getDate().format(Common.monthDateDayFormatter),
                newAttendance.getTime().format(Common.hourMinuteFormatter),
                newAttendance.findStatus().getMeaning()
        );
    }

    public void printExceptionMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAttendanceModifyResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.println();
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                oldAttendance.getDate().format(Common.monthDateDayFormatter),
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
                    attendance.getDate().format(Common.monthDateDayFormatter),
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
        if (attendance.getTime().equals(Common.noneAttendanceTime)) {
            return "--:--";
        }
        return attendance.getTime().format(Common.hourMinuteFormatter);
    }

    public void printPenaltyResult(Map<Crew, AttendanceStatistic> penaltyTargetCrews) {
        System.out.println();
        System.out.println("제적 위험자 조회 결과");
        for (Crew crew : penaltyTargetCrews.keySet()) {
            AttendanceStatistic statistic = penaltyTargetCrews.get(crew); //TODO : calculate할 값은 모두 dto에 go
            Map<AttendanceStatus, Integer> attendanceStatus = statistic.calculateStatusCountUntilBefore(
                    DateGenerator.now());
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    crew.getName(),
                    attendanceStatus.get(AttendanceStatus.ABSENCE),
                    attendanceStatus.get(AttendanceStatus.LATE),
                    statistic.calculatePenaltyUntilBefore(DateGenerator.now()).getMeaning()
            );
        }
    }

    private List<AttendanceStatistic> sortStatistics(List<AttendanceStatistic> statistics) {
        //TODO : 정렬
        return statistics;
    }
}
