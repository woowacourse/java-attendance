package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public final class OutputView {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printAttendanceResult(LocalDate date, String time, String attendanceStatusMessage) {
        String dayOfWeekKorean = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf(
                "%d월 %s일 %s %s (%s)\n",
                date.getMonthValue(),
                parseFullDay(date.getDayOfMonth()),
                dayOfWeekKorean,
                time,
                attendanceStatusMessage
                );
    }

    public void printUpdateResult(
            LocalDate date,
            String beforeTime,
            String beforeAttendanceStatus,
            String afterTime,
            String afterAttendanceStatus
    ) {
        String dayOfWeekKorean = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %s일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                date.getMonthValue(),
                parseFullDay(date.getDayOfMonth()),
                dayOfWeekKorean,
                beforeTime,
                beforeAttendanceStatus,
                afterTime,
                afterAttendanceStatus
                );
    }

    public void printIntroAttendanceRecord() {
        System.out.println("이번 달 빙티의 출석 기록입니다.\n");
    }

    public void printAttendancesCount(int attendanceCount, int lateCount, int absenceCount) {
        System.out.printf("\n출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n\n", absenceCount);
    }

    public void printPenaltyStatus(String penalty) {
        if (Objects.equals(penalty, "해당없음")) {
            return;
        }
        System.out.printf("%s 대상자입니다.\n", penalty);
    }

    public void printPenaltyResultMessage() {
        System.out.println("제적 위험자 조회 결과");
    }

    public void printPenaltyTarget(String nickName, int lateCount, int absenceCount, String penalty) {
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", nickName, absenceCount, lateCount, penalty);
    }

    private String parseFullDay(int part) {
        if (part < 10) {
            return "0" + part;
        }
        return "" + part;
    }
}
