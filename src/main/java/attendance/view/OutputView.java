package attendance.view;

import attendance.constant.Holiday;
import attendance.domain.Attendance;
import attendance.util.DateUtil;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    private OutputView() {}

    public static void printRecordAttendanceResult(Attendance attendance) {
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s)%n%n",
                attendance.getAttendDate().getMonthValue(),
                attendance.getAttendDate().getDayOfMonth(),
                attendance.getAttendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.getAttendTime().getHour(),
                attendance.getAttendTime().getMinute(),
                attendance.determineStatus().getName());
    }

    public static void printEditAttendanceResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n%n",
                oldAttendance.getAttendDate().getMonthValue(),
                oldAttendance.getAttendDate().getDayOfMonth(),
                oldAttendance.getAttendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                oldAttendance.getAttendTime().getHour(),
                oldAttendance.getAttendTime().getMinute(),
                oldAttendance.determineStatus().getName(),
                newAttendance.getAttendTime().getHour(),
                newAttendance.getAttendTime().getMinute(),
                newAttendance.determineStatus().getName());
    }

    public static void printAttendanceRecordsUntilYesterday(String nickname, LocalDate today, List<Attendance> attendances) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", nickname);
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(today.getYear(), today.getMonthValue(), day);
            if (DateUtil.isWeekend(currentDate) || Holiday.isHoliday(currentDate)) {
                continue;
            }
            printAttendanceRecords(attendances, currentDate);
        }
        System.out.println();
    }

    private static void printAttendanceRecords(List<Attendance> attendances, LocalDate currentDate) {
        attendances.stream()
                .filter(attendance -> attendance.isSameDate(currentDate))
                .findFirst()
                .ifPresentOrElse(OutputView::printExistRecords, () -> printNotExistRecords(currentDate));
    }

    private static void printExistRecords(Attendance attendance) {
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)%n",
                attendance.getAttendDate().getMonthValue(),
                attendance.getAttendDate().getDayOfMonth(),
                attendance.getAttendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.getAttendTime().getHour(),
                attendance.getAttendTime().getMinute(),
                attendance.determineStatus().getName());
    }

    private static void printNotExistRecords(LocalDate currentDate) {
        System.out.printf("%02d월 %02d일 %s --:-- (결석)%n",
                currentDate.getMonthValue(),
                currentDate.getDayOfMonth(),
                currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
