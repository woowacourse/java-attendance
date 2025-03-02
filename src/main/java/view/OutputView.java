package view;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void displayAttendanceCheck(LocalDate nowDate, LocalTime attendTime, String status) {
        System.out.println();
        printMonthAndDayOfMonth(nowDate);
        printTimeAndMinute(attendTime, status);
        System.out.println();
    }

    public void displayAttendanceEdit(LocalDate date, LocalTime originTime, String originStatus, LocalTime updatedTime,
                                      String updatedStatus) {
        System.out.println();
        printMonthAndDayOfMonth(date);
        printTimeAndMinute(originTime, originStatus);
        System.out.print(" -> ");
        printTimeAndMinute(updatedTime, updatedStatus);
        System.out.printf(" 수정 완료!%n");
    }

    public void displayAttendanceEditWithAbsence(LocalDate date, LocalTime updatedTime, String updatedStatus) {
        System.out.println();
        printMonthAndDayOfMonth(date);
        System.out.printf("--:-- (%s) -> ", AttendanceStatus.ABSENCE.getDescription());
        printTimeAndMinute(updatedTime, updatedStatus);
        System.out.printf(" 수정 완료!%n");
    }

    public void displayRecordMessage(String name) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n", name);
    }

    public void displayRecord(LocalDate nowDate, LocalTime time, String status) {
        printMonthAndDayOfMonth(nowDate);
        printTimeAndMinute(time, status);
        System.out.println();
    }

    public void displayAbsenceRecord(LocalDate nowDate) {
        printMonthAndDayOfMonth(nowDate);
        System.out.printf("--:-- (%s)%n", AttendanceStatus.ABSENCE.getDescription());
    }

    public void displayPenaltyCount(int attendanceCount, int latenessCount, int absenceCount) {
        System.out.printf("%n출석: %d회%n", attendanceCount);
        System.out.printf("지각: %d회%n", latenessCount);
        System.out.printf("결석: %d회%n", absenceCount);
    }

    public void displayPenaltyStatus(String status) {
        System.out.printf("%n%s 대상자입니다.%n", status);
    }

    public void displayExpulsionRiskCrewMessage() {
        System.out.printf("%n제적 위험자 조회 결과%n");
    }

    public void displayExpulsionRiskCrew(String name, int absenceCount, int latenessCount, String penaltyStatus) {
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
