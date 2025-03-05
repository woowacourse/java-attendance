package view;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.DangerousStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(LocalDateTime attendanceTime, AttendanceStatus attendanceStatus) {
        printAttendance(attendanceTime, attendanceStatus);
        System.out.println();
        System.out.println();
    }

    public void printAttendance(LocalDateTime attendanceTime, AttendanceStatus attendanceStatus) {
        System.out.println();
        System.out.printf("%d월 %02d일 %s %02d:%02d (%s)",
                attendanceTime.getMonthValue(),
                attendanceTime.getDayOfMonth(),
                attendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendanceTime.getHour(),
                attendanceTime.getMinute(),
                attendanceStatus.getStatus()
        );
    }

    public void printErrorMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    public void printModifiedAttendance(LocalDateTime modifiedAttendanceTime,
                                        AttendanceStatus modifiedAttendanceStatus) {
        System.out.printf("-> %02d:%02d (%s) 수정 완료!",
                modifiedAttendanceTime.getHour(),
                modifiedAttendanceTime.getMinute(),
                modifiedAttendanceStatus.getStatus());
        System.out.println();
        System.out.println();
    }

    public void printAttendanceRecord(String nickname, AttendanceRecord attendanceRecord, LocalDate date) {
        System.out.printf("이번 달 %s의 출석 기록입니다.", nickname);
        System.out.println();

        printRecords(attendanceRecord);

        int attendance = attendanceRecord.countStatus(AttendanceStatus.ATTENDANCE);
        int late = attendanceRecord.countStatus(AttendanceStatus.LATE);
        int absence = attendanceRecord.countStatus(AttendanceStatus.ABSENCE);

        printStatusCount(attendance, late, absence);
        printDangerousStatus(DangerousStatus.of(late, absence));
    }

    private void printRecords(AttendanceRecord attendanceRecord) {
        List<LocalDate> openDays = attendanceRecord.getOpenDays();
        for (LocalDate currentDay : openDays) {
            if (attendanceRecord.isAbsent(currentDay)) {
                System.out.println();
                System.out.printf("%d월 %02d일 %s --:-- (결석)",
                        currentDay.getMonthValue(), currentDay.getDayOfMonth(),
                        currentDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
                continue;
            }
            printAttendance(
                    attendanceRecord.findAttendanceTimeByDay(currentDay.getDayOfMonth()),
                    attendanceRecord.getAttendanceStatus(currentDay.getDayOfMonth()));
        }
        System.out.println();
    }

    private void printStatusCount(int attendance, int late, int absence) {
        System.out.println();
        System.out.printf("출석: %d회", attendance);
        System.out.println();
        System.out.printf("지각: %d회", late);
        System.out.println();
        System.out.printf("결석: %d회", absence);
        System.out.println();
        System.out.println();
    }

    private void printDangerousStatus(DangerousStatus dangerousStatus) {
        System.out.printf("%s 대상자입니다.", dangerousStatus.getStatus());
        System.out.println();
        System.out.println();
    }

    public void printDangerousCrews(List<Crew> crews) {
        Comparator<Crew> comparator = dangerousCrewComparator();
        crews.sort(comparator);

        System.out.println("제적 위험자 조회 결과");
        crews.forEach(crew -> {
            int absence = crew.getAttendanceRecord().countStatus(AttendanceStatus.ABSENCE);
            int late = crew.getAttendanceRecord().countStatus(AttendanceStatus.LATE);
            System.out.printf("%s: 결석 %d회, 지각 %d회 (%s)",
                    crew.getNickname(), absence, late,
                    DangerousStatus.of(late, absence).getStatus());
            System.out.println();
        });
        System.out.println();
    }

    private Comparator<Crew> dangerousCrewComparator() {
        return (crew1, crew2) -> {
            int i = crew2.getAttendanceRecord().totalAbsenceCount() - crew1.getAttendanceRecord().totalAbsenceCount();
            if (i == 0) {
                int j = crew2.getAttendanceRecord().countStatus(AttendanceStatus.LATE)
                        % DangerousStatus.LATE_TO_ABSENCE_RATE
                        - crew1.getAttendanceRecord().countStatus(AttendanceStatus.LATE)
                        % DangerousStatus.LATE_TO_ABSENCE_RATE;
                if (j == 0) {
                    return crew1.getNickname().compareTo(crew2.getNickname());
                }
                return j;
            }
            return i;
        };
    }
}
