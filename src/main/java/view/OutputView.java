package view;

import domain.Attendance;
import domain.ModifyResult;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public static String getFormattedDayInfo(LocalDate date) {
        return date.getMonthValue() + "월 " + date.getDayOfMonth() + "일 " + date.getDayOfWeek().getDisplayName(
                TextStyle.FULL, Locale.KOREAN);
    }

    public static void printMenu() {
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
    }

    public static void printCheckedAttendance(Attendance attendance) {
        System.out.println("\n" + getCheckedAttendance(attendance) + "\n");
    }

    public static String getCheckedAttendance(Attendance attendance) {
        return getFormattedDayInfo(attendance.getLocalDate()) + " "
                + attendance.getLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA))
                + " (" + attendance.getAttendanceStatus().getValue() + ")";
    }

    public static void printModifyResult(ModifyResult modifyResult) {
        System.out.println(
                getCheckedAttendance(modifyResult.getOldAttendance())
                        + " -> "
                        + modifyResult.getNewAttendance().getLocalTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA))
                        + " (" + modifyResult.getNewAttendance().getAttendanceStatus().getValue() + ") 수정 완료!"
        );

    }
}
