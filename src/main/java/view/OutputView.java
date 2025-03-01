package view;

import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import domain.CustomDayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OutputView {
    public void printAttendanceDetail(Attendance attendance) {
        LocalDate todayDate = attendance.getDay().getDate();
        int month = todayDate.getMonthValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeek = CustomDayOfWeek.getInstance(todayDate).getName();
        String attendanceStatus = getAttendanceStatus(attendance);
        String attendanceTime = getAttendanceTime(attendance);
        System.out.printf("%d월 %d일 %s %s (%s)\n", month, dayOfMonth, dayOfWeek, attendanceTime, attendanceStatus);
    }

    public void printModifiedAttendanceDetail(Attendance originAttendance, Attendance modifiedAttendance) {
        LocalDate date = modifiedAttendance.getDay().getDate();
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
        String dayOfWeek = CustomDayOfWeek.getInstance(date).getName();

        String originalAttendanceTime = getAttendanceTime(originAttendance);
        String originalAttendanceStatus = getAttendanceStatus(originAttendance);

        String modifiedAttendanceTime = getAttendanceTime(modifiedAttendance);
        String modifiedAttendanceStatus = getAttendanceStatus(modifiedAttendance);

        System.out.printf("%d월 %d일 %s %s (%s) -> %s (%s) 수정 완료!\n", month, dayOfMonth, dayOfWeek,
                originalAttendanceTime, originalAttendanceStatus, modifiedAttendanceTime, modifiedAttendanceStatus);
    }

    public void printAttendanceHistory(String nickname, Attendances attendances) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", nickname);
        List<Attendance> attendanceHistory = attendances.getAttendances();
        attendanceHistory.sort(Comparator.comparing(attendance -> attendance.getDay().getDate()));

        removeTodayHistory(attendanceHistory);

        printAttendances(attendanceHistory);

        printEachStatusCount(attendances);
        printPenaltyStatus(attendances);
    }

    public void printPenaltyCrews(Map<Crew, Attendances> penaltyCrews) {
        List<Crew> crews = new ArrayList<>(penaltyCrews.keySet());
        sortPenaltyCrews(penaltyCrews, crews);

        for (Crew crew : crews) {
            int absentCount = penaltyCrews.get(crew).getAbsentCount();
            int lateCount = penaltyCrews.get(crew).getLateCount();
            String penaltyName = penaltyCrews.get(crew).getPenaltyStatus().getName();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", crew.getNickname(), absentCount, lateCount, penaltyName);
        }
    }

    private void sortPenaltyCrews(Map<Crew, Attendances> penaltyCrews, List<Crew> crews) {
        crews.sort(
                Comparator.comparing((Crew crew) -> penaltyCrews.get(crew).getPenaltyStatus().getPoint(),
                                Comparator.reverseOrder())
                        .thenComparing(
                                (Crew crew) -> penaltyCrews.get(crew).getLateCount() + penaltyCrews.get(
                                        crew).getAbsentCount(),
                                Comparator.reverseOrder())
                        .thenComparing(Crew::getNickname));
    }

    private void printEachStatusCount(Attendances attendances) {
        int lateCount = attendances.getLateCount();
        int absentCount = attendances.getAbsentCount();
        int attendanceCount = attendances.getTotalCount() - lateCount - absentCount;
        System.out.printf("\n출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n", absentCount);
    }

    private void printPenaltyStatus(Attendances attendances) {
        if (attendances.getPenaltyStatus() == null) {
            return;
        }
        System.out.printf("\n%s 대상자입니다.\n\n", attendances.getPenaltyStatus().getName());
    }

    private void printAttendances(List<Attendance> attendanceHistory) {
        for (Attendance attendance : attendanceHistory) {
            int month = attendance.getDay().getDate().getMonthValue();
            int dayOfMonth = attendance.getDay().getDate().getDayOfMonth();
            String dayOfWeek = CustomDayOfWeek.getInstance(attendance.getDay().getDate()).getName();
            String attendanceTime = getAttendanceTime(attendance);

            String attendanceStatus = getAttendanceStatus(attendance);
            System.out.printf("%d월 %02d일 %s %s (%s)\n", month, dayOfMonth, dayOfWeek, attendanceTime, attendanceStatus);
        }
    }

    private void removeTodayHistory(List<Attendance> attendanceHistory) {
        if (attendanceHistory.getLast().getDay().getDate().equals(LocalDate.now())) {
            attendanceHistory.removeLast();
        }
    }

    private String getAttendanceStatus(Attendance attendance) {
        if (attendance.isAbsent()) {
            return "결석";
        }
        if (attendance.isLate()) {
            return "지각";
        }
        return "출석";
    }

    private String getAttendanceTime(Attendance attendance) {
        if (attendance.getTime() != null) {
            return attendance.getTime().toString();
        }
        return "--:--";
    }
}
