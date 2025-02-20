package view;

import domain.AttendTime;
import domain.AttendanceHistory;
import domain.AttendanceStatus;
import domain.Crew;
import domain.December;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public void printCrewAttendance(Crew crew) {
        for (int date : December.getWeekDays()) {
            AttendTime attendTime = crew.findAttendanceByDate(date);
            if (attendTime != null) {
                System.out.println(attendTime.checkTime());
                continue;
            }

            System.out.printf("12월 %d일 %s --:-- (결석)", date, December.getDayByDate(date));
            System.out.println();

        }

        AttendanceHistory attendanceHistory = crew.getAttendanceHistory();
        System.out.printf("출석: %d회", attendanceHistory.calculateOnTime());
        System.out.println();
        System.out.printf("지각: %d회", attendanceHistory.calculateLate());
        System.out.println();
        System.out.printf("결석: %d회", attendanceHistory.calculateAbsent());

        AttendanceStatus attendanceStatus = attendanceHistory.getAttendanceStatus();
        System.out.println();
        System.out.printf("%s 대상자입니다.", attendanceStatus.getStatus());
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

}
