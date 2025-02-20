package view;

import domain.AttendTime;
import domain.AttendanceHistory;
import domain.AttendanceStatus;
import domain.Crew;
import domain.December;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public void printTodayAttendance(final AttendTime attendTime) {
        System.out.println();
        printAttendTime(attendTime);
        System.out.println();
        System.out.println();
    }

    public void printBeforeChangedCrewAttendance(final AttendTime attendTime) {
        System.out.println();
        printAttendTime(attendTime);
    }

    public void printChangedCrewAttendance(String time, String status) {
        System.out.printf(" -> %s (%s) 수정 완료!", time, status);
        System.out.println();
        System.out.println();
    }

    public void printCrewAttendance(Crew crew) {
        for (int date : December.getWeekDays()) {
            AttendTime attendTime = crew.findAttendanceByDate(date);
            if (attendTime != null) {
                printAttendTime(attendTime);
                System.out.println();
                continue;
            }

            System.out.printf("12월 %02d일 %s --:-- (결석)", date, December.getDayByDate(date));
            System.out.println();
        }

        System.out.println();

        AttendanceHistory attendanceHistory = crew.getAttendanceHistory();
        System.out.printf("출석: %d회", attendanceHistory.calculateOnTime());
        System.out.println();
        System.out.printf("지각: %d회", attendanceHistory.calculateLate());
        System.out.println();
        System.out.printf("결석: %d회", attendanceHistory.calculateAbsent());
        System.out.println();

        System.out.println();
        AttendanceStatus attendanceStatus = attendanceHistory.getAttendanceStatus();
        System.out.printf("%s 대상자입니다.\n", attendanceStatus.getStatus());
        System.out.println();
    }


    public void printDismissalCrews(List<Crew> dismissalCrews, List<Crew> interviewCrews, List<Crew> warningCrews) {
        System.out.println("제적 위험자 조회 결과");

        printDismissalCrewsByType(dismissalCrews);
        printDismissalCrewsByType(interviewCrews);
        printDismissalCrewsByType(warningCrews);
    }

    private void printDismissalCrewsByType(List<Crew> dangerousCrews) {
        Comparator<Crew> comparator = (c1, c2) -> {
            int i = (c2.getAttendanceHistory().calculateLate() / 3 + c2.getAttendanceHistory().calculateAbsent())
                    - (c1.getAttendanceHistory().calculateLate() / 3 + c1.getAttendanceHistory().calculateAbsent());
            if (i == 0) {
                int j = (c2.getAttendanceHistory().calculateLate() % 3) - (c1.getAttendanceHistory().calculateLate()
                        % 3);
                if (j == 0) {
                    return c1.getName().compareTo(c2.getName());
                }
                return j;
            }
            return i;
        };

        dangerousCrews.sort(comparator);
        dangerousCrews.forEach(crew -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)", crew.getName(),
                    crew.getAttendanceHistory().calculateAbsent(),
                    crew.getAttendanceHistory().calculateLate(),
                    crew.getAttendanceHistory().getAttendanceStatus().getStatus());
            System.out.println();
        });
    }

    private void printAttendTime(final AttendTime attendTime) {
        System.out.printf("12월 %02d일 %s %02d:%02d (%s)",
                attendTime.getAttendTime().getDayOfMonth(),
                December.getDayByDate(attendTime.getAttendTime().getDayOfMonth()),
                attendTime.getAttendTime().getHour(),
                attendTime.getAttendTime().getMinute(),
                attendTime.checkTime()
        );
    }


}
