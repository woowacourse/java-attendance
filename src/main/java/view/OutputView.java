package view;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printAttendanceCheck(LocalDate nowDate, Attendance attendance) {
        System.out.println();
        printMonthAndDayOfMonth(nowDate);
        printTimeAndMinute(attendance.getTime(), attendance.determineStatus().getDescription());
        System.out.println();
    }

    public void printAttendanceEdit(LocalDate editDate, Attendance originAttendance, Attendance updatedAttendance) {
        if (originAttendance == null) {
            printAttendanceEditWithAbsence(editDate, updatedAttendance.getTime(),
                    updatedAttendance.determineStatus().getDescription());
            return;
        }
        System.out.println();
        printMonthAndDayOfMonth(editDate);
        printTimeAndMinute(originAttendance.getTime(), originAttendance.determineStatus().getDescription());
        System.out.print(" -> ");
        printTimeAndMinute(updatedAttendance.getTime(), updatedAttendance.determineStatus().getDescription());
        System.out.printf(" 수정 완료!%n");
    }

    public void printAttendanceEditWithAbsence(LocalDate date, LocalTime updatedTime, String updatedStatus) {
        System.out.println();
        printMonthAndDayOfMonth(date);
        System.out.printf("--:-- (%s) -> ", AttendanceStatus.ABSENCE.getDescription());
        printTimeAndMinute(updatedTime, updatedStatus);
        System.out.printf(" 수정 완료!%n");
    }

    public void printRecordMessage(String name) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n", name);
    }

    public void printRecord(LocalDate nowDate, Attendance attendance) {
        if (attendance == null) {
            printAbsenceRecord(nowDate);
            return;
        }
        printMonthAndDayOfMonth(nowDate);
        printTimeAndMinute(attendance.getTime(), attendance.determineStatus().getDescription());
        System.out.println();
    }

    public void printAbsenceRecord(LocalDate nowDate) {
        printMonthAndDayOfMonth(nowDate);
        System.out.printf("--:-- (%s)%n", AttendanceStatus.ABSENCE.getDescription());
    }

    public void printPenaltyCount(int attendanceCount, int latenessCount, int absenceCount) {
        System.out.printf("%n출석: %d회%n", attendanceCount);
        System.out.printf("지각: %d회%n", latenessCount);
        System.out.printf("결석: %d회%n", absenceCount);
    }

    public void printPenaltyStatus(Penalty penalty) {
        if (penalty.isPenaltyCrew()) {
            System.out.printf("%n%s 대상자입니다.%n", penalty.getDescription());
        }
    }

    public void printExpulsionRiskCrewList(List<Crew> riskCrewResult, LocalDate nowDate) {
        printExpulsionRiskCrewMessage();
        for (Crew crew : riskCrewResult) {
            printExpulsionRiskCrew(crew.getName(), crew.calculateAbsenceCount(nowDate),
                    crew.calculateLatenessCount(nowDate), crew.determinePenaltyStatus(nowDate).getDescription());
        }
    }

    public void printExpulsionRiskCrewMessage() {
        System.out.printf("%n제적 위험자 조회 결과%n");
    }

    public void printExpulsionRiskCrew(String name, int absenceCount, int latenessCount, String penaltyStatus) {
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", name, absenceCount, latenessCount, penaltyStatus);
    }

    private void printMonthAndDayOfMonth(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("%d월 %02d일 %s ", date.getMonthValue(), date.getDayOfMonth(), dayOfWeek);
    }

    private void printTimeAndMinute(LocalTime time, String status) {
        System.out.printf("%02d:%02d (%s)", time.getHour(), time.getMinute(), status);
    }
}
