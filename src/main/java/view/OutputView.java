package view;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void displayAttendanceCheck(LocalDate nowDate, LocalTime attendTime, String status) {
        String dayOfWeek = nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %02d일 %s ", nowDate.getMonthValue(), nowDate.getDayOfMonth(), dayOfWeek);
        System.out.printf("%02d:%02d (%s)%n", attendTime.getHour(), attendTime.getMinute(), status);
    }

    public void displayAttendanceEdit(LocalDate date, Attendance originAttendance, Attendance updatedAttendance) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %02d일 %s ", date.getMonthValue(), date.getDayOfMonth(), dayOfWeek);
        printAttendanceInfo(originAttendance);
        System.out.printf("%02d:%02d (%s) 수정 완료!%n", updatedAttendance.getTime().getHour(),
                updatedAttendance.getTime().getMinute(), updatedAttendance.determineStatus().getDescription());
    }

    public void displayRecordMessage(String name) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", name);
    }

    public void displayRecord(LocalDate nowDate, Attendance attendance) {
        String dayOfWeek = nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %02d일 %s ", nowDate.getMonthValue(), nowDate.getDayOfMonth(), dayOfWeek);
        printRecordInfo(attendance);
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
        System.out.println("제적 위험자 조회 결과");
    }

    public void displayExpulsionRiskCrew(String name, int absenceCount, int latenessCount, String penaltyStatus) {
        System.out.printf("%s: 결석 %d회, 지각 %d회 (%s)%n", name, absenceCount, latenessCount, penaltyStatus);
    }

    private void printRecordInfo(Attendance attendance) {
        if (attendance == null) {
            System.out.printf("--:-- (%s)%n", AttendanceStatus.ABSENCE.getDescription());
            return;
        }
        System.out.printf("%02d:%02d (%s)%n", attendance.getTime().getHour(),
                attendance.getTime().getMinute(), attendance.determineStatus().getDescription());
    }

    private void printAttendanceInfo(Attendance originAttendance) {
        if (originAttendance == null) {
            System.out.printf("--:-- (%s) -> ", AttendanceStatus.ABSENCE.getDescription());
            return;
        }
        System.out.printf("%02d:%02d (%s) -> ", originAttendance.getTime().getHour(),
                originAttendance.getTime().getMinute(), originAttendance.determineStatus().getDescription());
    }
}
