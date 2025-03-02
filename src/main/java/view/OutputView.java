package view;

import static domain.AttendanceCode.ABSENT;

import domain.Attendance;
import domain.AttendanceStatistics;
import domain.Attendances;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import util.DayConverter;

public class OutputView {
    private static final String ABSENT_TIME = "--:--";

    public void printAllLog(String name, Attendances attendances, AttendanceStatistics attendanceStatistics) {
        System.out.printf("\n이번 달 %s의 출석 기록입니다.\n\n", name);

        List<Attendance> sortedAttendances = attendances.getAttendances().stream()
                .sorted()
                .toList();

        for (Attendance attendance : sortedAttendances) {
            printAttendanceLog(attendance);
            System.out.println();
        }

        printAttendanceStatistic(attendanceStatistics);
    }

    public void printAttendanceStatistic(AttendanceStatistics attendanceStatistics) {
        System.out.printf("\n출석: %d회\n", attendanceStatistics.present());
        System.out.printf("지각: %d회\n", attendanceStatistics.late());
        System.out.printf("결석: %d회\n", attendanceStatistics.absent());
        System.out.printf("\n%s 대상자입니다.", attendanceStatistics.alertCode().getName());
    }

    public void printAttendanceLog(Attendance attendance) {
        String time = attendance.getAttendanceTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate date = attendance.getAttendanceTime().toLocalDate();
        if (attendance.calculateAttendanceCode() == ABSENT) {
            time = ABSENT_TIME;
        }
        System.out.printf("%d월 %02d일 %s %s (%s)",
                date.getMonth().getValue(),
                date.getDayOfMonth(),
                DayConverter.getKoreanDayOfWeek(date),
                time,
                attendance.calculateAttendanceCode().getName()
        );
    }

    public void printAlertCrews(Map<String, AttendanceStatistics> alertCrews) {
        System.out.println("제적 위험자 조회 결과");
        for (Entry<String, AttendanceStatistics> alertCrew : alertCrews.entrySet()) {
            printAlertCrew(alertCrew.getKey(), alertCrew.getValue());
        }
    }

    public void printAlertCrew(String name, AttendanceStatistics attendanceStatistics) {
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                name,
                attendanceStatistics.absent(),
                attendanceStatistics.late(),
                attendanceStatistics.alertCode().getName());
    }

    public void printChangeLog(Attendance originalAttendance,
                               Attendance changedAttendance) {

        String time = changedAttendance.getAttendanceTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        if (changedAttendance.calculateAttendanceCode() == ABSENT) {
            time = ABSENT_TIME;
        }

        printAttendanceLog(originalAttendance);
        System.out.printf(" -> %s (%s) 수정 완료!", time,
                changedAttendance.calculateAttendanceCode().getName());
    }

    public void printExceptionLog(Exception e) {
        System.out.println("\n[ERROR]" + e.getMessage());
    }
}
